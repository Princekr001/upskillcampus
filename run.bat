@echo off
echo Compiling the Banking Information System...
javac -d out src\com\bank\Main.java src\com\bank\model\*.java src\com\bank\service\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation failed! Please check your code.
    pause
    exit /b %ERRORLEVEL%
)

echo Compilation successful! Starting the application...
echo ===================================================
java -cp out com.bank.Main
pause
