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
    private JPanel keyboardPanel;
    private JButton[] letterButtons;
    private DrawingPanel drawingPanel;

    public GamePanel(GameLogic gameLogic, Runnable onGameOver) {
        this.gameLogic = gameLogic;
        this.onGameOver = onGameOver;
        setLayout(new BorderLayout());
        setBackground(Constants.BACKGROUND_COLOR);

        // Drawing Panel for Hangman and Word
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        // Keyboard Panel
        keyboardPanel = new JPanel(new GridLayout(3, 9, 5, 5));
        keyboardPanel.setBackground(Constants.BACKGROUND_COLOR);
        keyboardPanel.setBorder(BorderFactory.createEmptyPadding(10, 10, 10, 10));
        
        letterButtons = new JButton[26];
        for (int i = 0; i < 26; i++) {
            char c = (char) ('A' + i);
            JButton button = new JButton(String.valueOf(c));
            button.setFont(Constants.SMALL_FONT);
            button.setBackground(Color.WHITE);
            button.setFocusable(false);
            button.addActionListener(e -> {
                handleGuess(c);
                button.setEnabled(false);
            });
            letterButtons[i] = button;
            keyboardPanel.add(button);
        }
        add(keyboardPanel, BorderLayout.SOUTH);

        // Keyboard Listener for physical typing
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char guess = Character.toUpperCase(e.getKeyChar());
                if (guess >= 'A' && guess <= 'Z') {
                    handleGuess(guess);
                }
            }
        });
    }

    private void handleGuess(char guess) {
        if (gameLogic.makeGuess(guess)) {
            // Disable button if it exists
            int index = guess - 'A';
            if (index >= 0 && index < 26) {
                letterButtons[index].setEnabled(false);
                letterButtons[index].setBackground(gameLogic.getTargetWord().indexOf(guess) != -1 ? 
                    new Color(46, 204, 113) : new Color(231, 76, 60)); // Green if correct, Red if wrong
            }
            repaint();
            if (gameLogic.isGameOver()) {
                onGameOver.run();
            }
        }
    }

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            setBackground(Constants.BACKGROUND_COLOR);
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

            int centerX = getWidth() / 4;
            int baseY = 450;

            // Base
            g2.drawLine(centerX - 100, baseY, centerX + 100, baseY);
            // Vertical pole
            g2.drawLine(centerX, baseY, centerX, 100);
            // Top horizontal pole
            g2.drawLine(centerX, 100, centerX + 200, 100);
            // Rope
            g2.drawLine(centerX + 200, 100, centerX + 200, 150);

            int errors = Constants.MAX_LIVES - gameLogic.getRemainingLives();
            int x = centerX + 200;

            if (errors > 0) g2.drawOval(x - 25, 150, 50, 50); // Head
            if (errors > 1) g2.drawLine(x, 200, x, 320);     // Body
            if (errors > 2) g2.drawLine(x, 230, x - 40, 280); // Left arm
            if (errors > 3) g2.drawLine(x, 230, x + 40, 280); // Right arm
            if (errors > 4) g2.drawLine(x, 320, x - 40, 400); // Left leg
            if (errors > 5) g2.drawLine(x, 320, x + 40, 400); // Right leg
        }

        private void drawWord(Graphics2D g2) {
            g2.setFont(Constants.WORD_FONT);
            g2.setColor(Constants.TEXT_COLOR);
            String display = gameLogic.getDisplayWord();
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(display)) / 2 + 100;
            int y = getHeight() / 2;
            g2.drawString(display, x, y);
        }

        private void drawStats(Graphics2D g2) {
            g2.setFont(Constants.MAIN_FONT);
            g2.setColor(Constants.ACCENT_COLOR);
            g2.drawString("Lives: " + gameLogic.getRemainingLives(), 30, 40);
        }
    }
}
