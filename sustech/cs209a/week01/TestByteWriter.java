import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestByteWriter {
    private static final String file_path = "output.txt";

    @Test
    public void testByteWriter() {
        try (FileOutputStream fos = new FileOutputStream(file_path)) {
            final byte[] buffer = new byte[65535];
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (byte) i;
            }
            fos.write(buffer);
            fos.flush();//fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }

    }

}
