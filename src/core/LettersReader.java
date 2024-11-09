package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LettersReader implements IReader {
    public void readToMap(InputStream input, Map<String, Integer> map) throws IOException {
        InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);

        int c;
        while ((c = reader.read()) != -1) {
            char character = (char) c;

            if (Character.isLetter(character)) {
                var str = Character.toString(character);
                map.put(str, map.getOrDefault(str, 0) + 1);
            }
        }
    }
}
