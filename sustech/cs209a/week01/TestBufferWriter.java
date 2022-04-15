import org.junit.jupiter.api.Test;

import java.io.*;

public class TestBufferWriter {
    private static final String file_path = "output2_gb18030.txt";

    @Test
    public void testBufferWriter() {
        try (FileOutputStream fos = new FileOutputStream(new File(file_path));
             OutputStreamWriter osw = new OutputStreamWriter(fos, "gb18030");
             BufferedWriter bWriter = new BufferedWriter(osw);) {
            bWriter.write("你好！\n");
//			bWriter.write(100);
            bWriter.write("100");
            bWriter.write(" 分 \n");
            bWriter.write("送给你！\n");
            bWriter.flush();//bWriter.close();

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
