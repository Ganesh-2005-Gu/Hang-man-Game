package hangman.ui;

import hangman.logic.GameLogic;
import hangman.logic.WordProvider;
import hangman.util.Constants;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private WordProvider wordProvider;
    private GameLogic gameLogic;
    private GamePanel gamePanel;

    public GameFrame() {
        setTitle(Constants.GAME_TITLE);
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        wordProvider = new WordProvider();
        startNewGame();
    }

    private void startNewGame() {
        gameLogic = new GameLogic(wordProvider.getRandomWord());
        if (gamePanel != null) {
            remove(gamePanel);
        }
        gamePanel = new GamePanel(gameLogic, this::handleGameOver);
        add(gamePanel);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();
    }

    private void handleGameOver() {
        String message;
        if (gameLogic.isGameWon()) {
            message = "Congratulations! You won!\nThe word was: " + gameLogic.getTargetWord();
        } else {
            message = "Game Over! You lost.\nThe word was: " + gameLogic.getTargetWord();
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }
}
