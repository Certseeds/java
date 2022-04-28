package DocumentPreprocessing;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class TestDirs {
    @Test
    public void test_files() {
        String str1 = "test1/test2/test3";
        File files = new File(str1);
        files.mkdirs();
    }

    @Test
    public void test_transfer() {
        String str1 = "test1/test2/test3/file1.txt";
        String str2 = "test1/test2/test4/file2.txt";
        File file_read = new File(str1);
        StringBuffer test = new StringBuffer();
        try (BufferedReader test_br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file_read), "GB18030"))) {
            while (test_br.ready()) {
                test.append((char) test_br.read());
                // 转成char加到StringBuffer对象中
            }
            System.out.println(test);
        } catch (FileNotFoundException ffe) {
            System.out.println("dont find");
        } catch (IOException e) {
            System.out.println("exception");
        }
        try (FileOutputStream fos = new FileOutputStream(str2);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bW = new BufferedWriter(osw);) {
            bW.write(test.toString());
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
}
