import org.junit.jupiter.api.Test;

import java.io.*;

public class TestBufferReader {
    private static final String file_path = "test_resources/sample.txt";

    @Test
    public void testBufferReader() {
        try (FileInputStream fis = new FileInputStream(file_path);
             InputStreamReader isr = new InputStreamReader(fis, "gb18030");
             BufferedReader bReader = new BufferedReader(isr);) {
            char[] cbuf = new char[65535];
            int file_len = bReader.read(cbuf);
            System.out.println(file_len);
            System.out.println(cbuf);
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
