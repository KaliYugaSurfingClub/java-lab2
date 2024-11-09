package core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class LettersFrequencyDictionary implements port.IFrequencyDictionary {
    private final HashMap<Character, Integer> map;

    public LettersFrequencyDictionary() {
        this.map = new HashMap<>();
    }

    public void read(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);

        int c;
        while ((c = reader.read()) != -1) {
            char character = (char) c;

            if (Character.isLetter(character)) {
                map.put(character, map.getOrDefault(character, 0) + 1);
            }
        }
    }

    public Map<Character, Integer> getFrequencies() {
        return Collections.unmodifiableMap(map);
    }
}
