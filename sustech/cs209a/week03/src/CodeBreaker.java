import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.HashMap;

public class CodeBreaker{
    public static void main(String[] args) {
        try {
            System.out.println(breaker(args));
        } catch (IllegalArgumentException iae) {
            System.out.println(String.format("arguments' number should be 1 not %d", args.length));
        }
    }

    public static String breaker(String[] args) throws IllegalArgumentException {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        StringBuilder strb = new StringBuilder();
        try {
            strb = readFile(args[0]);
        } catch (FileAlreadyExistsException fee) {
            return (fee.getMessage() + " is a folder and it exist");
        } catch (FileNotFoundException ffe) {
            return (String.format("%s do not exist", args[0]));
        } catch (IOException e) {
            return ("unknown IO exception" + Arrays.toString(e.getStackTrace()));
        }
        HashMap<String, Integer> fres = new HashMap<>();
        int max_v = -1;
        for (int i = 0; i < strb.length() - 3; i++) {
            fres.put(strb.substring(i, i + 3),
                    fres.getOrDefault(strb.substring(i, i + 3), 0) + 1);
            max_v = Math.max(max_v, fres.get(strb.substring(i, i + 3)));
        }
        for (String key : fres.keySet()) {
            if (fres.get(key) == max_v) {
                return key;
            }
        }
        return "should not reach there";
    }

    public static StringBuilder readFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else if (file.exists() && file.isDirectory()) {
            throw new FileAlreadyExistsException(path);
        }
        StringBuilder strb = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        try (InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            int n = 0;
            while ((n = isr.read()) != -1) {
                strb.append((char) n);
            }
        } catch (UnsupportedEncodingException uee) {
            System.out.println("unsupported format of file");
        }

        return strb;
    }
}
