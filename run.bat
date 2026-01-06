@echo off
javac -d out src\hangman\util\Constants.java src\hangman\logic\WordProvider.java src\hangman\logic\GameLogic.java src\hangman\ui\GamePanel.java src\hangman\ui\GameFrame.java src\hangman\Main.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
java -cp out hangman.Main
pause
