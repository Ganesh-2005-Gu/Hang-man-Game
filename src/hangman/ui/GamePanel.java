package hangman.ui;

import hangman.logic.GameLogic;
import hangman.util.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private GameLogic gameLogic;
    private Runnable onGameOver;

    public GamePanel(GameLogic gameLogic, Runnable onGameOver) {
        this.gameLogic = gameLogic;
        this.onGameOver = onGameOver;
        setBackground(Constants.BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char guess = e.getKeyChar();
                if (Character.isLetter(guess)) {
                    if (gameLogic.makeGuess(guess)) {
                        repaint();
                        if (gameLogic.isGameOver()) {
                            onGameOver.run();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawHangman(g2);
        drawWord(g2);
        drawStats(g2);
    }

    private void drawHangman(Graphics2D g2) {
        g2.setColor(Constants.SECONDARY_COLOR);
        g2.setStroke(new BasicStroke(5));

        // Base
        g2.drawLine(50, 500, 250, 500);
        // Vertical pole
        g2.drawLine(150, 500, 150, 100);
        // Top horizontal pole
        g2.drawLine(150, 100, 350, 100);
        // Rope
        g2.drawLine(350, 100, 350, 150);

        int errors = Constants.MAX_LIVES - gameLogic.getRemainingLives();

        if (errors > 0) g2.drawOval(325, 150, 50, 50); // Head
        if (errors > 1) g2.drawLine(350, 200, 350, 350); // Body
        if (errors > 2) g2.drawLine(350, 250, 300, 300); // Left arm
        if (errors > 3) g2.drawLine(350, 250, 400, 300); // Right arm
        if (errors > 4) g2.drawLine(350, 350, 300, 450); // Left leg
        if (errors > 5) g2.drawLine(350, 350, 400, 450); // Right leg
    }

    private void drawWord(Graphics2D g2) {
        g2.setFont(Constants.WORD_FONT);
        g2.setColor(Constants.TEXT_COLOR);
        String display = gameLogic.getDisplayWord();
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(display)) / 2 + 100; // Offset for hangman
        int y = getHeight() / 2;
        g2.drawString(display, x, y);
    }

    private void drawStats(Graphics2D g2) {
        g2.setFont(Constants.MAIN_FONT);
        g2.setColor(Constants.ACCENT_COLOR);
        g2.drawString("Lives: " + gameLogic.getRemainingLives(), 50, 50);

        g2.setFont(Constants.SMALL_FONT);
        g2.setColor(Constants.TEXT_COLOR);
        g2.drawString("Guessed: " + gameLogic.getGuessedLetters().toString(), 50, 80);
    }
}
