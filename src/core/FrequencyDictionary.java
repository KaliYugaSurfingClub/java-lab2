package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FrequencyDictionary {
    private final HashMap<String, Integer> map;
    private final IReader reader;

    public FrequencyDictionary(IReader reader) {
        this.map = new HashMap<>();
        this.reader = reader;
    }

    public void read(InputStream input) throws IOException {
        reader.readToMap(input, map);
    }

    public Map<String, Integer> getFrequencies() {
        return Collections.unmodifiableMap(map);
    }
}
