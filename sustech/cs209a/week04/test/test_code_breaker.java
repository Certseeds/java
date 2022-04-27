import org.junit.jupiter.api.Test;

/**
 * @Github: https://github.com/Certseeds
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-03-10 16:28:30
 * @LastEditors : nanoseeds
 * @LastEditTime : 2020-03-10 16:28:30
 */
public class test_code_breaker {

    @Test
    public void test_sort_strArray() {
        final String str1 = "import java.io.ByteArrayOutputStream;\n" +
                "import java.io.FileNotFoundException;\n" +
                "import java.io.PrintStream;\n" +
                "import java.nio.file.FileAlreadyExistsException;\n" +
                "import java.util.Arrays;";
        final String str2 = "import static org.junit.jupiter.api.Assertions.*;";
        System.out.println(str1.substring(0, 6).equals(str2.substring(0, 6)));
        final StringBuffer sb = new StringBuffer();
        sb.append("114514");
        System.out.println(sb.indexOf(String.valueOf('a')));
    }

    @Test
    public void test_lab04_main() {
        code_breaker.main(new String[]{"secret.txt"});
    }
}
