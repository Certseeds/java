package DocumentPreprocessing;

import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Launcher {

    String targetEncoding;
    String prefix;
    String postfix;
    String inputCsvPath;
    String outputPath;
    String summaryPath;
    String inputTxtPath;
    List<CategorizeCriteria> categorizeCriteriaList;
    List<FileEntry> fileEntries = new ArrayList<>();

    public static void main(String[] args) {
        // prompt user if no config file is given
        if (0 == args.length) {
            throw new NullPointerException("Config.yml path not provided.");
        }

        // This part gives you a brief idea of how the text preprocessing program works.
        // You will not be able to compile and run the program now.
        // But don't worry! Check the methods one by one, and fill the empties with appropriate code,
        // then you can finish this task step by step.
        // Now go and start.
        // hint: You can also search for `UNTODO` to find all unfinished part.
        // hint: Before your start, read the `FileEntry` and `CategorizeCriteria` class we provided
        //       and think how you can utilize the code we given.
        Launcher launcher = new Launcher();
        launcher.parseConfigFile(args[0]);
        launcher.readInputCsv();
        launcher.processFiles();
        launcher.writeSummary();
        System.out.println("Done.");
    }

    public void parseConfigFile(String configFilePath) {

        // The config file is basically a map.
        // To help you get started, we already read the config file into a map.
        Yaml yaml = new Yaml();
        Map<String, Object> configMap = null;
        try (BufferedReader yamlReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(configFilePath), StandardCharsets.UTF_8))) {
            configMap = yaml.load(yamlReader);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Config file doesn't exist.");
        } catch (IOException e) {
            System.err.println("IO Exception happened.");
            e.printStackTrace();
        }

        if (null == configMap) {
            throw new IllegalStateException("Config map parse error");
        }
        // TODO: Read values from config files into those given variables. Examples are given.

        // The following items should have default values.
        // If they do not exist in configMap, plug the appropriate default value
        // hint: getOrDefault method in Map
        prefix = (String) configMap.getOrDefault("Prefix", "");
        postfix = (String) configMap.getOrDefault("Postfix", "");
        targetEncoding = (String) configMap.getOrDefault("TargetEncoding", "UTF-8");


        // If summary path does not exist in configMap, set it to null
        summaryPath = (String) configMap.getOrDefault("SummaryPath", null);

        // The following items are required.
        // If they are not shown, print out the error message
        // and exit the program with return code 1
        inputCsvPath = (String) configMap.getOrDefault("InputCsvPath", null);
        System.out.println(Files.exists(Paths.get(inputCsvPath)));
        if (null == inputCsvPath) {
            throw new NullPointerException("No input csv path define");
        }

        inputTxtPath = (String) configMap.getOrDefault("InputTxtPath", null);
        if (null == inputTxtPath) {
            throw new NullPointerException("No input txt path.");
        }
        outputPath = (String) configMap.getOrDefault("OutputPath", null);
        if (null == outputPath) {
            throw new NullPointerException("No output path.");
        }
        // We will make it easier for you with the categorize parameters. No need to touch them.
        if (configMap.containsKey("CategorizeBy")) {
            categorizeCriteriaList = ((List<String>) configMap.get("CategorizeBy")).stream()
                    .map(x -> CategorizeCriteria.valueOf(x.toUpperCase())).collect(Collectors.toList());
        }
    }

    public void readInputCsv() {
        // UNTODO: read info about the text files from the
        //  input csv into the `fileEntries` list
        // Don't forget the date format and skip the first line.

        CharsetDetector detector = new CharsetDetector();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        String[] header = new String[0];
        int[] map = {0, 1, 2, 3};
        String[] names = {"filename", "source", "field", "date"};
        ArrayList<String> input_csv = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputCsvPath),StandardCharsets.UTF_8))) {
            header = br.readLine().split(",");
            for (int j = 0; j < names.length; j++) {
                for (int i = 0; i < header.length; i++) {
                    if (names[j].equals(header[i])) {
                        map[j] = i;
                        break;
                    }
                }
            }
            String str_temp = "";
            while ((str_temp = br.readLine()) != null) {
                input_csv.add(str_temp);
            }
        } catch (FileNotFoundException fnfe) {
            throw new IllegalArgumentException("Input csv file do not exist");
        } catch (IOException e) {
            System.err.println("IO Exception happened.");
            e.printStackTrace();
        }
        if (!Files.exists(Paths.get(inputTxtPath))) {
            throw new IllegalArgumentException(inputTxtPath + " do not find");
        }
        for (String files : input_csv) {
            FileEntry fe = new FileEntry();
            String[] line = files.split(",");
            fe.setFile(new File(inputTxtPath + File.separator + line[map[0]]));
            if (!fe.getFile().exists()) {
                throw new IllegalArgumentException(fe.getFile().getName() + " do not exist");
            }
            fe.setSource(line[map[1]]);
            fe.setField(line[map[2]]);
            fe.setDate(LocalDate.parse(line[map[3]], dateTimeFormatter));
            try {
                detector.setText(Files.readAllBytes(fe.getFile().toPath()));
                CharsetMatch charsetMatch = detector.detect();
                String encoding = charsetMatch.getName();
                if (encoding.contains("GB")) {
                    encoding = "GB18030";
                } else if (encoding.contains("UTF-8")) {
                    encoding = "UTF-8";
                }
                fe.setEncoding(encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileEntries.add(fe);
        }

    }

    public void processFiles() {
        File newFolder = new File(outputPath);
        if (!newFolder.exists()) {
            throw new IllegalArgumentException("Output path does not exist.");
        }
        try {
            for (FileEntry entry : fileEntries) {
                System.out.println(entry.toString());
                final StringBuilder currentPath = new StringBuilder(newFolder.getPath() + File.separator);
                // entry.setNewPath(currentPath);
                // UnTODO choose it or the other one.
                if (null != categorizeCriteriaList) {
                    for (CategorizeCriteria cc : categorizeCriteriaList) {
                        currentPath.append(cc.toCategoryKey(entry)).append(File.separator);
                    }
                }
                final File folder = new File(currentPath.toString());
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                final String file_name = entry.getFile().getName();
                if (!file_name.contains(".")) {
                    currentPath.append(prefix).append(file_name).append(postfix);
                } else {
                    final String[] pair = file_name.split("\\.");
                    currentPath.append(prefix).append(pair[0]).append(postfix).append(".").append(pair[1]);
                }
                entry.setNewPath(currentPath.toString());
                //System.out.println(currentPath);
                // TODO: construct the new path for a given text file
                // don't forget to set the newPath attribute for the FileEntry object
                // hint: use a for-each structure with categorizeCriteriaList
                // hint: check out the toCategoryKey method in CategorizeCriteria,
                // as it might be useful for your code
                final File write_file = new File(currentPath.toString());
                final StringBuilder contest = new StringBuilder();
                try (BufferedReader test_br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(entry.getFile()), entry.getEncoding()))) {
                    while (test_br.ready()) {
                        contest.append((char) test_br.read());
                    }
                } catch (FileNotFoundException ffe) {
                    System.out.println("dont find");
                } catch (IOException e) {
                    System.out.println("exception");
                }
                entry.setLength(contest.length());
                try (FileOutputStream fos = new FileOutputStream(write_file);
                     OutputStreamWriter osw = new OutputStreamWriter(fos, targetEncoding);
                     BufferedWriter bW = new BufferedWriter(osw);) {
                    bW.write(contest.toString());
                    bW.flush();
                } catch (FileNotFoundException e) {
                    System.out.println("The pathname does not exist.");
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    System.out.println("The Character Encoding is not supported.");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Failed or interrupted when doing the I/O operations");
                    e.printStackTrace();
                }
                // TODO: detect the encoding, read the original file, and write to the new file
                // hint: refer to Examples.java
            }
        } catch (Exception e) {
            System.err.println("File processing error.");
            e.printStackTrace();
        }
    }
    public void writeSummary() {
        // no need to write summary if the summary path is not set
        if (null == summaryPath) {
            return;
        }
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        File Summary = new File(summaryPath);
        File Parent = new File(Summary.getParent());
        if (!Parent.exists()) {
            Parent.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(Summary);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bW = new BufferedWriter(osw);) {
            bW.write("path,source,field,date,char_count,old_encoding\n");
            for (FileEntry entry : fileEntries) {
                /*bW.write(String.format("%s,%s,%s,%s,%d,%s\n",
                        entry.getNewPath(),
                        entry.getSource(),
                        entry.getField(),
                        outputFormat.format(entry.getDate()),
                        entry.getLength(),
                        entry.getEncoding()
                ));*/
                bW.write(String.format("%s\n", entry.toSummaryString()));
            }
            bW.flush();
        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }
        // UnTODO: write summary to the file specified by summaryPath
        // Don't forget the first line. You can refer to our task PDF for what's in the first line.
    }
}
