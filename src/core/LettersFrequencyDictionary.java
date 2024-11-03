package core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LettersFrequencyDictionary implements port.IFrequencyDictionary {
    private final HashMap<Character, Integer> map;

    public LettersFrequencyDictionary() {
        this.map = new HashMap<>();
    }

    public void Read(InputStream input) throws IOException {
        int c;

        while ((c = input.read()) != -1) {
            char charStr = Character.toLowerCase((char) c);
            if (Character.isLetter(charStr)) {
                map.put(charStr, map.getOrDefault(charStr, 0) + 1);
            }
        }
    }

    public List<HashMap.Entry<Character, Integer>> getAscendingFrequencies(boolean ascending) {
        List<HashMap.Entry<Character, Integer>> sortedList = new ArrayList<>(map.entrySet());

        sortedList.sort((entry1, entry2) -> {
            int comparison = entry1.getValue().compareTo(entry2.getValue());
            return ascending ? comparison : -comparison;
        });

        return sortedList;
    }
}
