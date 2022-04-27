import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;


public class test_code_breaker {
    private static final String path_true = "secret.txt";
    private static final String path_false = "secret.log";
    private static final String path_dir = "src";
    private static final String path_unsupport = "un.txt";

    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
    }

    @Test
    public void test_argument_main() {
        final String[] arr = {path_true, path_dir, path_false, path_unsupport};
        assertDoesNotThrow(() -> CodeBreaker.main(arr));
        assertEquals(
                String.format("arguments' number should be 1 not %d\r\n", arr.length),
                outContent.toString());
    }

    @Test
    public void test_argument_number_1() {
        final String[] arrat = {path_true, path_dir, path_false, path_unsupport};
        assertThrows(IllegalArgumentException.class, () -> {
            CodeBreaker.breaker(arrat);
        });
    }

    @Test
    public void test_main_true() {
        final String[] arr = {path_true};
        CodeBreaker.main(arr);
        assertEquals(";48\r\n", outContent.toString());
    }

    @Test
    public void test_main_not_exist() {
        final String[] arr = {path_false};
        CodeBreaker.main(arr);
        assertEquals(
                String.format("%s do not exist\r\n", arr[0]),
                outContent.toString());
    }

    @Test
    public void test_main_already_exist() {
        final String[] arr = {path_dir};
        CodeBreaker.main(arr);
        assertEquals(
                String.format("%s is a folder and it exist\r\n", arr[0]),
                outContent.toString());
    }

    @Test
    public void test_read_file_success() {
        assertDoesNotThrow(() -> {
            CodeBreaker.readFile(path_true);
        });
    }

    @Test
    public void test_read_file_no_file() {
        assertThrows(FileNotFoundException.class, () -> {
            CodeBreaker.readFile(path_false);
        });
    }

    @Test
    public void test_read_file_exist_but_dict() {
        assertThrows(FileAlreadyExistsException.class, () -> {
            CodeBreaker.readFile(path_dir);
        });
    }

    @Test
    public void test_read_file_unsupproted_foramt() {
        assertDoesNotThrow(() -> {
            CodeBreaker.readFile(path_unsupport);
        });

    }
}
