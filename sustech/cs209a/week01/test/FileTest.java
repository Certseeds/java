import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileTest {
    public static boolean createFile(String destFileName) {
        final File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("Create single file " + destFileName + " fail,target file already exists！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("Create single file " + destFileName + " fail，target file cannot be a directory！");
            return false;
        }
        // Check if the directory where target file is located exists
        if (!file.getParentFile().exists()) {
            // if directory where target file is located doesn't exist, create
            // its' parent directory.
            System.out.println("directory where target file is located doesn't exist, create its' parent directory！");
            final File parentFile = file.getParentFile();
            final boolean parentFileMkdir = parentFile.mkdirs();
            if (!parentFileMkdir) {
                System.out.println("Create directory where target file is located fails！");
                return false;
            }
        }
        // Create target file
        try {
            if (file.createNewFile()) {
                System.out.println("Create single file " + destFileName + " success！");
                return true;
            } else {
                System.out.println("Create single file " + destFileName + " fail！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Create single file " + destFileName + " fail！" + e.getMessage());
            return false;
        }
    }

    @Test
    public void fileTest() {

        final String osName = System.getProperties().getProperty("os.name");
        System.out.println(osName);

        final String fileSeperator = System.getProperties().getProperty("file.separator");
        System.out.println(fileSeperator);

        // if your opereating system is windows, your fileName should be like this：
        final String fileName1 = "\temp.log";
        // or your operating system is Linux, your fileNamd should be like this：
        final String fileName2 = "/tmp/test.txt";

        // Otherwise, considering cross-platform, you'd better do so
        // Create file
        final String fileName = "." + fileSeperator + "tmp2" + fileSeperator + "tempFile.txt";
        FileTest.createFile(fileName);

    }

}
