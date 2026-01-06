# Hangman Game

A simple Hangman game built with Java and Swing.

## Features
- Random word selection from a list.
- Graphical representation of the hangman.
- Track remaining lives and guessed letters.
- Responsive keyboard input.

## How to Run
1. Ensure you have JDK installed.
2. Compile the project:
   ```bash
   javac src/hangman/util/Constants.java src/hangman/logic/WordProvider.java src/hangman/logic/GameLogic.java src/hangman/ui/GamePanel.java src/hangman/ui/GameFrame.java src/hangman/Main.java -d out
   ```
3. Run the game:
   ```bash
   java -cp out hangman.Main
   ```

## Controls
- Use your keyboard to type letters and guess the word.
