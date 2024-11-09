package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface IReader {
    void readToMap(InputStream input, Map<String, Integer> map) throws IOException;
}
