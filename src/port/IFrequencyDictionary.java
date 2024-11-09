package port;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public interface IFrequencyDictionary {
    void read(InputStream input) throws IOException;
    Map<Character, Integer> getFrequencies();
}
