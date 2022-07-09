import java.io.*;

public class CharsetConvertor {

    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            InputStreamReader isr = new InputStreamReader(fis, args[1]);
            BufferedReader bReader = new BufferedReader(isr);
            StringBuilder sBuilder = new StringBuilder();
            while (true) {
                final String str = bReader.readLine();
                if (null == str) {
                    break;
                }
                final byte[] bytes = str.getBytes(args[2]);
                int i = 0;
                if (args[2].equals("UTF-16")) {
                    i = 2;
                }
                for (; i < bytes.length; i++) {
                    sBuilder.append(String.format("%02X ", bytes[i]));
                    System.out.printf("%02X ", bytes[i]);
                }
                sBuilder.append("\n");
            }
            isr.close();

            try (BufferedWriter bWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(args[2] + "_" + args[0])));) {
                bWriter.write(sBuilder.toString());
            }
        } catch (Exception e) {
            // UnTODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
