import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TestCharSample {

    @Test
    public void testCharSample() {
        // UTF-16:赵-8d75 耀-8000, GB:赵-D5D4 耀-D2AB
        final char c = '赵';
        final int value = c;
        System.out.printf("%s\n", c);
        System.out.printf("%X\n", value);

        final String str = "赵耀"; // UTF-16
        try {
            final byte[] bytes1 = str.getBytes("GBK"); // or GBK
            for (byte b : bytes1) {
                System.out.printf("%2X ", b);
            }
            System.out.println();
            final byte[] bytes2 = str.getBytes(StandardCharsets.UTF_16);
            for (byte b : bytes2) {
                System.out.printf("%02X ", b);
            }
            System.out.println();

            final byte[] bytes3 = str.getBytes(StandardCharsets.UTF_16BE);
            for (byte b : bytes3) {
                System.out.printf("%02X ", b);
            }
            System.out.println();

            final byte[] bytes4 = str.getBytes(StandardCharsets.UTF_16LE);
            for (byte b : bytes4) {
                System.out.printf("%02X ", b);
            }
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        }
    }
}
