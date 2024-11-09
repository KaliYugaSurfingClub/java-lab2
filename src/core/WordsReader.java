package core;

import core.IReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WordsReader implements IReader {
    public void readToMap(InputStream input, Map<String, Integer> map) throws IOException {
        var reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        var word = new StringBuilder();

        int c;
        while ((c = reader.read()) != -1) {
            char character = (char) c;

            if (Character.isLetter(character)) {
                word.append(character);
            } else if (!word.isEmpty()) {
                String str = word.toString().toLowerCase();
                map.put(str, map.getOrDefault(str, 0) + 1);
                word.setLength(0);
            }
        }

        if (!word.isEmpty()) {
            String str = word.toString().toLowerCase();
            map.put(str, map.getOrDefault(str, 0) + 1);
        }
    }
}
