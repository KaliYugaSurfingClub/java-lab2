package port;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IFrequencyDictionary {
    void Read(InputStream input) throws IOException;
    List<HashMap.Entry<Character, Integer>> getAscendingFrequencies(boolean ascending);
}
