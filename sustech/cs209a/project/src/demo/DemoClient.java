package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.http.client.fluent.Request;
import util.Operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static util.Utils.calculateMD5;
import static util.Utils.get_files_from_dir;
import static util.Utils.readFile_guess_format;
import static util.Utils.store_file;

/**
 * The type Demo client.
 */
public class DemoClient {
    /**
     * The Endpoint, server's address.
     */
    static final String endpoint = "http://localhost:7002";
    /**
     * The download files's store in here.
     */
    static final String begin = "./download/";
    /**
     * The Object mapper. use for deserialize.
     */
    static ObjectMapper objectMapper = new ObjectMapper();
    static Magic mime_parser = new Magic();
    //private static org.apache.logging.log4j.Logger logger2 = LogManager.getLogger(DemoClient.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments, use as input string array.
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        //System.out.println(logger.getLevel());
        Scanner in = new Scanner(System.in);
        while (true) {
            args = in.nextLine().split("\\s+");
            if (args.length < 1) {
                System.err.println("should have parameter");
                printUsage();
                continue;
            }
            Operation operation = Operation.parseOperation(args[0]);
            switch (operation) {
                case UPLOAD: {
                    if (args.length < 2) {
                        System.err.println("parameter should bigger than 1");
                        printUsage();
                        break;
                    }
                    handleUpload(args);
                    break;
                }
                case DOWNLOAD: {
                    if (args.length < 2) {
                        System.err.println("parameter should bigger than 1");
                        printUsage();
                        break;
                    }
                    handleDownload(args);
                    break;
                }
                case COMPARE: {
                    if (args.length < 3) {
                        System.err.println("parameter should bigger than 2");
                        printUsage();
                        break;
                    }
                    handleCompare(args);
                    break;
                }
                case EXISTS: {
                    if (args.length < 2) {
                        System.err.println("parameter should bigger than 1");
                        printUsage();
                        break;
                    }
                    handleExists(args);
                    break;
                }
                case LIST: {
                    handle_Files(args);
                    break;
                }
                case BREAK: {
                    System.out.println("client end");
                    return;
                }
                default: {
                    System.err.println("Unknown operation,input once again");
                    printUsage();
                    break;
                }
            }
        }
    }

    /**
     * Handle exists.
     *
     * @param args the args, String array, should have >= 2 Strings, but only 2nd will be use.
     * @throws IOException the io exception
     */
    public static void handleExists(String[] args) throws IOException {
        System.out.println("begin Exists");
        String Path = args[1];
        System.out.println(Path);
        File file = new File(Path);
        if (!(file.exists() && file.isFile() && file.canRead())) {
            System.err.printf("Wrong Path of %s", Path);
            return;
            // if can not read.
        }
        String str = readFile_guess_format(file);
        String md5 = calculateMD5(str);
        String responseString = null;
        try {
            responseString = Request.Get(endpoint + "/files/" + md5 + "/exists")
                    .execute()
                    .returnContent()
                    .asString();
            // get result
            Map<String, Object> response = (Map<String, Object>) objectMapper.readValue(responseString, Map.class);
            Map<String, Object> result = (Map<String, Object>) response.get("result");
            if (result.get("exists").equals(true)) {
                System.out.printf("The file in %s is in database.\n", Path);
            } else {
                System.out.printf("The file in %s not in database.\n", Path);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Handle upload.
     *
     * @param args the args, String array, it should have >=2 Strings, all args[i](i>0) will be upload.
     *             multiply file or folder is supported.
     * @throws IOException the io exception
     */
    public static void handleUpload(String[] args) throws IOException {
        System.out.println("begin Upload");
        ArrayList<File> files = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            files.addAll(get_files_from_dir(new File(args[i])));
        }
        HashSet<File> hs_file = new HashSet<>();
        hs_file.addAll(files);
        for (File file : hs_file) {
            MagicMatch match = new MagicMatch();
            try {
                match = mime_parser.getMagicMatch(Files.readAllBytes(file.toPath()));
            } catch (MagicException | MagicParseException | MagicMatchNotFoundException e) {
                e.printStackTrace();
            }
            if (!match.getMimeType().contains("text")) {
                System.err.printf("Path %s is not text, upload fail.", file.getAbsolutePath());
                continue;
            }
            if (!(file.exists() && file.isFile() && file.canRead())) {
                System.err.printf("Wrong Path of %s", file.getAbsolutePath());
                continue;
                // if can not read.
            }
            String str = readFile_guess_format(file);
            String md5 = calculateMD5(str);
            String responseString = null;
            try {
                responseString = Request.Post(endpoint + "/files/" + md5)
                        .bodyByteArray(str.getBytes())
                        .execute()
                        .returnContent()
                        .asString();
                Map<String, Object> response = (Map<String, Object>) objectMapper.readValue(responseString, Map.class);
                Map<String, Object> result = (Map<String, Object>) response.get("result");
                // get result
                if (result.get("success").equals(true) && response.get("code").equals(0)) {
                    System.out.printf("The file in %s is upload database.\n", file.getAbsolutePath());
                } else {
                    System.err.printf("The file in %s happen error,\n" +
                            " error code is %s,\n" +
                            "message is %s \n", file.getAbsolutePath(), response.get("code"), response.get("message"));
                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }

    }

    /**
     * Handle compare.
     *
     * @param args the args, String array, it should have >=3 Strings:
     *             2nd and 3rd will be use to compare
     *             all files should be upload to server.
     * @throws IOException the io exception
     */
    public static void handleCompare(String[] args) throws IOException {
        System.out.println("begin Compare");
        String Path1 = args[1];
        String Path2 = args[2];
        File file1 = new File(Path1);
        File file2 = new File(Path2);
        if (!(file1.exists() && file1.isFile() && file1.canRead())) {
            System.err.printf("Wrong Path of %s", Path1);
            return;
        }
        if (!(file2.exists() && file2.isFile() && file2.canRead())) {
            System.err.printf("Wrong Path of %s", Path2);
            return;
        }
        String str1 = readFile_guess_format(file1);
        String str2 = readFile_guess_format(file2);
        String md51 = calculateMD5(str1);
        String md52 = calculateMD5(str2);
        String responseString = null;
        try {
            responseString = Request.Get(endpoint + "/files/" + md51 + "/compare/" + md52)
                    .execute()
                    .returnContent()
                    .asString();
            Map<String, Object> response = (Map<String, Object>) objectMapper.readValue(responseString, Map.class);
            Map<String, Object> result = (Map<String, Object>) response.get("result");
            // get result
            if (response.get("code").equals(0)) {
                System.out.printf("distance between %s and %s is :,simple distance : %s, Levennshtein Distance : %s\n",
                        Path1, Path2, result.get("simple_similarity"), result.get("levenshtein_distance"));
            } else {
                System.err.printf("Happen error,\n" +
                        " error code is %s,\n" +
                        "message is %s \n", response.get("code"), response.get("message"));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Handle download.
     *
     * @param args the args,String array, it should have >=2 Strings, 2nd  will be use to download files .
     * @throws IOException the io exception
     */
    public static void handleDownload(String[] args) throws IOException {
        System.out.println("begin downloading");
        String Path = args[1];
        File file = new File(Path);
        if (!(file.exists() && file.isFile() && file.canRead())) {
            System.err.printf("Wrong Path of %s", Path);
            return;
        }
        String str = readFile_guess_format(file);
        String md5 = calculateMD5(str);
        String responseString = null;
        try {
            responseString = Request.Get(endpoint + "/files/" + md5)
                    .execute()
                    .returnContent()
                    .asString();
            Map<String, Object> response = (Map<String, Object>) objectMapper.readValue(responseString, Map.class);
            Map<String, Object> result = (Map<String, Object>) response.get("result");
            // get result
            if (response.get("code").equals(0)) {
                String content = (String) result.get("content");
                store_file(begin + md5 + ".txt", content);
                System.out.printf("The file is download in %s.\n", begin + md5 + ".txt");
            } else {
                System.err.printf("The file in %s happen error,\n" +
                        " error code is %s,\n" +
                        "message is %s \n", Path, response.get("code"), response.get("message"));
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Handle files.
     *
     * @param args the args, no args will be use.
     * @throws IOException the io exception
     */
    public static void handle_Files(String[] args) throws IOException {
        System.out.println("begin list");
        String responseString = null;
        try {
            responseString = Request.Get(endpoint + "/files")
                    .execute()
                    .returnContent()
                    .asString();
            Map<String, Object> response = (Map<String, Object>) objectMapper.readValue(responseString, Map.class);
            Map<String, Object> result = (Map<String, Object>) response.get("result");
            ArrayList<LinkedHashMap<String, Object>> summ_list = (ArrayList<LinkedHashMap<String, Object>>) result.get("files");
            // get result
            int count = 0;
            for (LinkedHashMap<String, Object> lh : summ_list) {
                System.out.printf("file %d :\n md5 is : %s,\nlength is %s,\npreview is %s\n",
                        count, lh.get("md5"), lh.get("length"), lh.get("preview"));
                count++;
                // output them one by one.
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Print usage.
     */
    public static void printUsage() {
        System.out.println("Usage: [op] [params]");
        System.out.println("Available Operation: upload, download, compare, exists, list, break.");
        // add a break command to finish the clien
    }

}
