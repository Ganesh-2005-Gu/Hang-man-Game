package hangman.logic;

import hangman.util.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordProvider {
    private List<String> words;
    private Random random;

    public WordProvider() {
        words = new ArrayList<>();
        random = new Random();
        loadWords();
    }

    private void loadWords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.WORDS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(line.trim().toUpperCase());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading words: " + e.getMessage());
            // Fallback word
            words.add("HANGMAN");
        }
        
        if (words.isEmpty()) {
            words.add("HANGMAN");
        }
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
