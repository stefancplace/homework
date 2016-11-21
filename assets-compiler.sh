#!/bin/bash

while [[ true ]]; do
    node ../main/assets-compiler.js -doWatchCompiler -repo cplace-customer-repo-skeleton -chdir ../main %1 %2 %3 %4
    result=${?}
    if [[ $result == 1 ]]; then
        echo "Got result: ${result} - stopping."
        break;
    fi
    echo "Compiler finished - restarting!"
done
