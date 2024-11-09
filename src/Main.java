import UI.UI;
import core.LettersFrequencyDictionary;

public class Main {
    public static void main(String[] args) {
        var fd = new LettersFrequencyDictionary();
        var ui = new UI(fd);

        ui.run();
    }
}