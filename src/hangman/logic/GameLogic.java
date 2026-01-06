package hangman.logic;

import hangman.util.Constants;
import java.util.HashSet;
import java.util.Set;

public class GameLogic {
    private String targetWord;
    private Set<Character> guessedLetters;
    private int remainingLives;
    private boolean gameOver;
    private boolean gameWon;

    public GameLogic(String word) {
        reset(word);
    }

    public void reset(String word) {
        this.targetWord = word.toUpperCase();
        this.guessedLetters = new HashSet<>();
        this.remainingLives = Constants.MAX_LIVES;
        this.gameOver = false;
        this.gameWon = false;
    }

    public boolean makeGuess(char letter) {
        if (gameOver) return false;
        
        letter = Character.toUpperCase(letter);
        if (guessedLetters.contains(letter)) {
            return false;
        }

        guessedLetters.add(letter);

        if (targetWord.indexOf(letter) == -1) {
            remainingLives--;
            if (remainingLives <= 0) {
                gameOver = true;
                gameWon = false;
            }
        } else {
            if (isWordGuessed()) {
                gameOver = true;
                gameWon = true;
            }
        }
        return true;
    }

    private boolean isWordGuessed() {
        for (char c : targetWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public String getDisplayWord() {
        StringBuilder display = new StringBuilder();
        for (char c : targetWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                display.append(c).append(" ");
            } else {
                display.append("_ ");
            }
        }
        return display.toString().trim();
    }

    public String getTargetWord() {
        return targetWord;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }
}
