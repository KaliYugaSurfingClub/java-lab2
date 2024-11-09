package UI;

import port.IFrequencyDictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


public class UI {
    private final IFrequencyDictionary dict;

    public UI(IFrequencyDictionary dict) {
        this.dict = dict;
    }

    public void Run() {
        read();
        write();
    }

    private void read() {
        var inputFilePaths = promptForInputFilePaths();

        for (String path : inputFilePaths) {
            try (FileInputStream fis = new FileInputStream(path)) {
                dict.Read(fis);
            } catch (IOException e) {
                System.out.printf("Error: %s while reading file: %s\n", e.getMessage(), path);
            }
        }
    }

    private void write() {
        var outputFilePath = promptForOutputFilePath();

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            for (Map.Entry<Character, Integer> entry : dict.getFrequencies().entrySet()) {
                var line = String.format("Letter: '%c', Frequency: %d\n", entry.getKey(), entry.getValue());
                fos.write(line.getBytes(StandardCharsets.UTF_8));
            }

            System.out.printf("file %s was filled\n", outputFilePath);
        } catch (IOException e) {
            System.out.printf("Error writing to output file: %s\n", e.getMessage());
        }
    }

    private List<String> promptForInputFilePaths() {
        var scanner = new Scanner(System.in);
        var filePaths = new ArrayList<String>();

        while (true) {
            System.out.print("Enter input file path: ");

            var filePath = scanner.nextLine();
            var file = new File(filePath);
            var absolute = file.getAbsolutePath();

            if (!file.exists()) {
                System.out.printf("Input file not found: %s, please try again.\n", absolute);
                continue;
            }

            if (file.isFile()) {
                System.out.printf("Input file found: %s\n", absolute);
                filePaths.add(file.getAbsolutePath());
                break;
            }

            if (file.isDirectory()) {
                System.out.printf("Input directory found: %s\n", absolute);
                try {
                    Files.walk(file.toPath())
                            .filter(Files::isRegularFile)
                            .forEach(path -> filePaths.add(path.toString()));
                    break;
                } catch (IOException e) {
                    System.out.printf("Error: %s collecting files from directory: %s, please try again.\n", e.getMessage(), absolute);
                }
            }
        }

        return filePaths;
    }

    private String promptForOutputFilePath() {
        var scanner = new Scanner(System.in);
        var filePath = "";

        while (true) {
            System.out.print("Enter output file path: ");

            filePath = scanner.nextLine();
            var file = new File(filePath);
            var absolute = file.getAbsolutePath();

            if (file.exists()) {
                System.out.printf("Output file found: %s\n", absolute);
                break;
            }

            try {
                if (file.createNewFile()) {
                    System.out.printf("Output file created: %s\n", absolute);
                    break;
                }

                System.out.printf("Could not create output file in %s, please try again.\n", absolute);
            } catch (IOException e) {
                System.out.printf("Error: %s creating output file: %s, please try again.\n", e.getMessage(), absolute);
            }
        }

        return filePath;
    }
}
