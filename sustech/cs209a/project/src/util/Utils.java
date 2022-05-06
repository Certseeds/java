package util;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

import javax.xml.bind.DatatypeConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Utils.
 */
public class Utils {
    /**
     * initial it for save time, use for produce levenshtein distance.
     */
    private static final LevenshteinDistance LD = new LevenshteinDistance();
    /**
     * initial it for save time
     */
    private static MessageDigest MD5_messageDigest;

    /**
     * this one is used for three place decimal
     */
    private static DecimalFormat df = new DecimalFormat("0.000");

    /**
     * this one used for guess the type of text.
     */
    private static CharsetDetector detector = new CharsetDetector();

    /**
     * Calculate md5 of a bytes array.
     *
     * @param bytes the bytes, a bytes array.
     * @return the string
     */
    public static String calculateMD5(byte[] bytes) {
        try {
            MD5_messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        MD5_messageDigest.update(bytes);
        return DatatypeConverter.printHexBinary(MD5_messageDigest.digest());
    }

    /**
     * Calculate md5 of a string.
     *
     * @param str the str
     * @return MD5 of the string
     */
    public static String calculateMD5(String str) {
        return calculateMD5(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gets levennshtein distance of two file.
     *
     * @param f1 File 1
     * @param f2 File 2
     * @return the levennshtein distance : a int value.
     * it is realize by apacha's libiary
     */
    public static int get_LevennshteinDistance(File f1, File f2) {
        String file1 = readFile(f1);
        String file2 = readFile(f2);
        return get_LevennshteinDistance(file1, file2);
    }

    /**
     * Gets levennshtein distance.
     *
     * @param f1 the string 1
     * @param f2 the string 2
     * @return the levennshtein distance : a int value.
     * it is realize by apacha's libiary
     */
    public static int get_LevennshteinDistance(String f1, String f2) {
        return LD.apply(f1, f2);
    }

    /**
     * Gets simple similarity.
     *
     * @param f1 the String1.
     * @param f2 the String2.
     * @return the simple similarity
     * is get by (different one by one) / std::min(f1.length(),f2.length())
     */
    public static String get_simple_similarity(String f1, String f2) {
        int max_length = Math.max(Math.max(f1.length(), f2.length()), 1);
        int common_size = 0;
        for (int i = 0; i < Math.min(f1.length(), f2.length()); i++) {
            common_size += (f1.charAt(i) == f2.charAt(i)) ? 1 : 0;
        }
        return df.format((double) common_size / max_length);
    }

    /**
     * Read file string.
     * this one thought the encode way is UTF-8, so it just use in server-end.
     *
     * @param file the file
     * @return the string
     */
    public static String readFile(File file) {
        StringBuilder strb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new
                     InputStreamReader(fis, StandardCharsets.UTF_8)) {
            int n = 0;
            while ((n = isr.read()) != -1) {
                strb.append((char) n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strb.toString();
    }

    /**
     * Read file guess format string.
     * this one thought the encode way is UTF-8, so it just use in server-end.
     *
     * @param file the file
     * @return the string
     */
    public static String readFile_guess_format(File file) {
        StringBuilder contest = new StringBuilder();
        try {
            detector.setText(Files.readAllBytes(file.toPath()));
            CharsetMatch charsetMatch = detector.detect();
            String encoding = charsetMatch.getName();
            try (BufferedReader test_br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), encoding))) {
                while (test_br.ready()) {
                    contest.append((char) test_br.read());
                }
            }
        } catch (FileNotFoundException ffe) {
            System.out.println("dont find");
        } catch (IOException e) {
            System.out.println("exception");
        }
        return contest.toString();
    }


    /**
     * Store file.
     *
     * @param Path  the path, String
     * @param bytes the bytes, bytes array.
     */
    public static void store_file(String Path, byte[] bytes) {
        store_file(Path, new String(bytes));
    }

    /**
     * Store file.
     *
     * @param Path the path, String.
     * @param str  the str, String that will be write in disk.
     */
    public static void store_file(String Path, String str) {
        try (FileOutputStream fos = new FileOutputStream(new File(Path));
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bW = new BufferedWriter(osw)) {
            bW.write(str);
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
    }

    public static List<File> get_files_from_dir(File file) {
        List<File> will_return = new ArrayList<>();
        if (file.isFile()) {
            will_return.add(file);
        } else if (file.isDirectory()) {
            for (File i : file.listFiles()) {
                List<File> temp = get_files_from_dir(i);
                will_return.addAll(temp);
            }
        }
        return will_return;
    }
}
