//#!/usr/bin/env node
var sys = require('util'), fs = require('fs'), path = require('path'), events = require('events'), exec = require('child_process').exec, os = require("os"), Q = require("q");
// Set to true for some debug logging.
var debug = false;
var simpleGitFetch = function (simpleGit, repoName) {
    var deferred = Q.defer();
    simpleGit.fetch(function (err, result) {
        if (err) {
            deferred.reject(err);
        }
        else {
            console.log('repo ' + repoName + ' successfully fetched');
            deferred.resolve(result);
        }
    });
    return deferred.promise;
};
var simpleGitStatus = function (simpleGit, repoName, repoProperties) {
    var deferred = Q.defer();
    simpleGit.status(function (err, result) {
        if (err) {
            deferred.reject(err);
        }
        else {
            if (result.not_added.length === 0 && result.deleted.length === 0 && result.modified.length === 0 && result.created.length === 0 && result.conflicted.length === 0) {
                debug && console.log('result of gitStatus', result);
                debug && console.log('setting commit to', result.current);
                repoProperties.branch = result.current;
                debug && console.log('repoProperties', repoProperties);
                deferred.resolve(result);
            }
            else {
                deferred.reject('working copy of repo ' + repoName + ' is not clean');
            }
        }
    });
    return deferred.promise;
};
var simpleGitClone = function (simpleGit, repoName, branch, url) {
    var deferred = Q.defer();
    simpleGit.clone(url, repoName, '--single-branch', '--branch', branch, function (err, result) {
        if (err) {
            deferred.reject(err);
        }
        else {
            deferred.resolve(result);
        }
    });
    return deferred.promise;
};
var simpleGitCheckoutBranch = function (simpleGit, repoName, branch) {
    var deferred = Q.defer();
    simpleGit.checkout(branch, function (err, result) {
        if (err) {
            deferred.reject(err);
        }
        else {
            console.log('repo ' + repoName + ' is now in branch', branch);
            deferred.resolve(result);
        }
    });
    return deferred.promise;
};
var simpleGitCheckoutCommit = function (simpleGit, repoName, commit) {
    var deferred = Q.defer();
    simpleGit.checkout(commit, function (err, result) {
        if (err) {
            deferred.reject(err);
        }
        else {
            console.log('repo ' + repoName + ' is now in commit', commit);
            deferred.resolve(result);
        }
    });
    return deferred.promise;
};
var simpleGitResetHard = function (simpleGit, repoName) {
    var deferred = Q.defer();
    simpleGit.reset('hard', function (err, result) {
        if (err) {
            deferred.reject(err);
        }
        else {
            console.log('repo ' + repoName + ' has been resetted');
            deferred.resolve(result);
        }
    });
    return deferred.promise;
};
var simpleGitRevParseHead = function (simpleGit, repoName, repoProperties) {
    var deferred = Q.defer();
    simpleGit.revparse(['HEAD'], function (err, commit) {
        if (err) {
            deferred.reject(err);
        }
        else {
            commit = commit.replace(/[\n\r]+/g, '');
            debug && console.log('repo ' + repoName + ' is in commit ' + commit);
            repoProperties.commit = commit;
            deferred.resolve(commit);
        }
    });
    return deferred.promise;
};
(function () {
    var i;
    var argv = process.argv;
    var WRITE_REPOS_STATE_LONG = 'write-current-repos-state';
    var WRITE_REPOS_STATE_SHORT = 'w';
    var UPDATE_REPOS_LONG = 'update-repos';
    var UPDATE_REPOS_SHORT = 'u';
    var CLONE_REPOS_LONG = 'clone-repos';
    var CLONE_REPOS_SHORT = 'c';
    var DEBUG_OPTION = '--verbose';
    var CH_DIR_OPTION = '--chdir';
    var obj;
    var repoName;
    var propertiesFileName = 'parent-repos.json';
    for (i = 0; i < argv.length; i++) {
        if (argv[i] === DEBUG_OPTION) {
            debug = true;
            console.log('debug mode has been enabled');
        }
    }
    for (i = 0; i < argv.length; i++) {
        if (argv[i] === CH_DIR_OPTION) {
            var dir = argv[i + 1];
            debug && console.log('chdir to', dir);
            process.chdir(dir);
        }
    }
    for (i = 0; i < argv.length; i++) {
        if (argv[i] === UPDATE_REPOS_LONG || argv[i] === UPDATE_REPOS_SHORT) {
            obj = JSON.parse(fs.readFileSync(propertiesFileName, 'utf8'));
            debug && console.log('properties', obj);
            for (repoName in obj) {
                if (obj.hasOwnProperty(repoName)) {
                    debug && console.log('repo', repoName);
                    var repoProperties = obj[repoName];
                    debug && console.log('repoProperties', repoProperties);
                    var commit = repoProperties.commit;
                    debug && console.log('commit', commit);
                    var branch = repoProperties.branch;
                    debug && console.log('branch', branch);
                    var simpleGit = require('simple-git')('../' + repoName);
                    simpleGitFetch(simpleGit, repoName).then(simpleGitStatus(simpleGit, repoName, repoProperties)).then(simpleGitCheckoutBranch(simpleGit, repoName, branch)).then(simpleGitCheckoutCommit(simpleGit, repoName, commit).then(simpleGitResetHard(simpleGit, repoName)));
                }
            }
            return;
        }
        else if (argv[i] === WRITE_REPOS_STATE_LONG || argv[i] === WRITE_REPOS_STATE_SHORT) {
            obj = JSON.parse(fs.readFileSync(propertiesFileName, 'utf8'));
            debug && console.log('properties', obj);
            var promises = [];
            for (repoName in obj) {
                if (obj.hasOwnProperty(repoName)) {
                    debug && console.log('repo', repoName);
                    var repoProperties = obj[repoName];
                    debug && console.log('repoProperties', repoProperties);
                    var simpleGit = require('simple-git')('../' + repoName);
                    promises.push(simpleGitStatus(simpleGit, repoName, repoProperties));
                    promises.push(simpleGitRevParseHead(simpleGit, repoName, repoProperties));
                }
            }
            Q.all(promises).then(function (result) {
                debug && console.log('status and revparse successfully completed');
                var newPropertiesAsString = JSON.stringify(obj, null, 2).replace(/[\n\r]/g, os.EOL);
                debug && console.log('newPropertiesAsString', newPropertiesAsString);
                fs.writeFile(propertiesFileName, newPropertiesAsString, function (err) {
                    if (err) {
                        return console.log('an error occured while writing ' + propertiesFileName, err);
                    }
                    else {
                        console.log(propertiesFileName + ' has been saved');
                    }
                });
            }, function (err) {
                console.log('an error occurred:', err);
            });
            return;
        }
        else if (argv[i] === CLONE_REPOS_LONG || argv[i] === CLONE_REPOS_SHORT) {
            obj = JSON.parse(fs.readFileSync(propertiesFileName, 'utf8'));
            debug && console.log('properties', obj);
            for (repoName in obj) {
                if (obj.hasOwnProperty(repoName)) {
                    debug && console.log('repo', repoName);
                    var repoProperties = obj[repoName];
                    debug && console.log('repoProperties', repoProperties);
                    var url = repoProperties.url;
                    debug && console.log('url', url);
                    var branch = repoProperties.branch;
                    debug && console.log('branch', branch);
                    var simpleGit = require('simple-git')('..');
                    simpleGitClone(simpleGit, repoName, branch, url);
                }
            }
            return;
        }
    }
    console.log('available options:');
    console.log('   ' + WRITE_REPOS_STATE_LONG + ' || ' + WRITE_REPOS_STATE_SHORT + ' [' + DEBUG_OPTION + ']');
    console.log('   ' + UPDATE_REPOS_LONG + ' || ' + UPDATE_REPOS_SHORT + ' [' + DEBUG_OPTION + ']');
    console.log('   ' + CLONE_REPOS_LONG + ' || ' + CLONE_REPOS_SHORT + ' [' + DEBUG_OPTION + ']');
    console.log('[' + DEBUG_OPTION + ']');
    console.log('[' + CH_DIR_OPTION + ' xxx]');
})();
