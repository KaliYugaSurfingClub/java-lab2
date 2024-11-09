package port;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public interface IFrequencyDictionary {
    void Read(InputStream input) throws IOException;
    Map<Character, Integer> getFrequencies();
}
