package UI;

import port.IFrequencyDictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
                System.out.println("Error reading file " + path + ": " + e.getMessage());
            }
        }
    }

    private void write() {
        var outputFilePath = promptForOutputFilePath();

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            for (HashMap.Entry<Character, Integer> entry : dict.getAscendingFrequencies(false)) {
                var line = buildLine(entry.getKey(), entry.getValue());
                fos.write(line.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            System.out.println("Error writing to output file: " + e.getMessage());
        }
    }

    private String buildLine(char letter, Integer count) {
        return String.format("Letter: '%c', Frequency: %d%n", letter, count);
    }

    private List<String> promptForInputFilePaths() {
        Scanner scanner = new Scanner(System.in);
        var filePaths = new ArrayList<String>();

        while (true) {
            System.out.print("Enter input file path: ");
            var filePath = scanner.nextLine();

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("Input file not found, please try again.");
                continue;
            }

            if (file.isFile()) {
                System.out.println("Input file found: " + file.getAbsolutePath());
                filePaths.add(file.getAbsolutePath());
                break;
            }

            if (file.isDirectory()) {
                System.out.println("Input directory found: " + file.getAbsolutePath());
                try {
                    List<String> collectedPaths = collectFilesFromDirectory(file.toPath());
                    filePaths.addAll(collectedPaths);
                    break;
                } catch (IOException e) {
                    System.out.println("Error collecting files from directory: " + e.getMessage() + " try again");
                }
            }
        }

        return filePaths;
    }

    private String promptForOutputFilePath() {
        Scanner scanner = new Scanner(System.in);
        String filePath;

        while (true) {
            System.out.print("Enter output file path: ");
            filePath = scanner.nextLine();

            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("Output file found: " + file.getAbsolutePath());
                break;
            }

            try {
                if (file.createNewFile()) {
                    System.out.println("Output file created: " + file.getAbsolutePath());
                    break;
                }

                System.out.println("Could not create output file, please try again.");
            } catch (IOException e) {
                System.out.println("An error occurred while creating the output file: " + e.getMessage());
            }
        }

        return filePath;
    }

    //todo return paths
    private List<String> collectFilesFromDirectory(Path directory) throws IOException {
        List<String> filePaths = new ArrayList<>();
        Files.walk(directory)
                .filter(Files::isRegularFile)
                .forEach(path -> filePaths.add(path.toString()));

        return filePaths;
    }
}
