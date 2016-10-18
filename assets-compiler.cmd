:: Run the assets-compiler in a loop, so it is automatically restarted when it fails.
:start
node ../main/assets-compiler.js -doWatchCompiler -repo cplace-customer-repo-skeleton -chdir ../main %1 %2 %3 %4
@echo Errorlevel %ERRORLEVEL%
:: Errors in the compiler yield positive errorlevel. Do not restart then.
:: When the compiler detects a change to itself, it returns errorlevel 0. Restart then.
:: On Windows, stopping with Ctrl-C yields a negative errorlevel. Restart then, if the user continues the script.
if errorlevel 1 goto end
@echo Compiler finished. Restarting!
goto start
:end
