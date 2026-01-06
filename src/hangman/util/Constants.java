package hangman.util;

import java.awt.Color;
import java.awt.Font;

public class Constants {
    public static final String GAME_TITLE = "Java Hangman";
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;
    
    public static final int MAX_LIVES = 6;
    
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Blue
    public static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark Grey
    public static final Color ACCENT_COLOR = new Color(231, 76, 60);    // Red
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Grey
    public static final Color TEXT_COLOR = new Color(44, 62, 80);
    
    public static final Font MAIN_FONT = new Font("SansSerif", Font.BOLD, 24);
    public static final Font WORD_FONT = new Font("Monospaced", Font.BOLD, 48);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 18);
    
    public static final String WORDS_FILE_PATH = "resources/words.txt";
}
