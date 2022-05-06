// SPDX-License-Identifier: AGPL-3.0-or-later

import dao.TextDao;
import demo.DemoClient;
import demo.DemoServer;
import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import util.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Random;
import java.util.UUID;

import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Test utils.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class test_Utils {
    private static final int upload_number = 18;
    private static final int un_upload_number = 8;
    private static ByteArrayInputStream in;
    private static ByteArrayOutputStream std_out;
    private static ByteArrayOutputStream std_err;
    private static Utils util;
    private static DemoServer DS;
    private static Random random = new Random(System.currentTimeMillis());
    private static CharsetDetector detector = new CharsetDetector();
    /**
     * The Sb.
     */
    StringBuilder sb;

    @BeforeAll
    private static void initial() throws ClassNotFoundException, IOException {
        deleteDirectory(new File("./download/"));
        deleteDirectory(new File("./upload/"));
        forceMkdir(new File("./download/"));
        forceMkdir(new File("./upload/"));
        util = new Utils();
        PrintStream console = System.out;
        // 获取System.out 输出流的句柄
        TextDao.delete_sql();
        DS = new DemoServer();
        DS.main(new String[0]);
    }

    private static void setOutput() {
        std_out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(std_out));
        std_err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(std_err));
    }

    @AfterAll
    private static void after_all() throws IOException {
        deleteDirectory(new File("./download/"));
        deleteDirectory(new File("./upload/"));
        forceMkdir(new File("./download/"));
        forceMkdir(new File("./upload/"));
    }

    /**
     * Before each.
     */
    @BeforeEach
    public void before_each() {
        sb = new StringBuilder();
        setOutput();
    }

    /**
     * Test get simple similarity.
     */
    @Test
    @Order(1)
    public void test_get_simple_similarity() {
        assertEquals(1.0f / 7.0f, Double.parseDouble(Utils.get_simple_similarity("114514", "1234567")), 1.0f / 1000.0f);

    }

    /**
     * Test exists not in when database is empty.
     *
     * @throws IOException the io exception
     */
// way come form https://kknews.cc/code/xq6a3pr.html
    @Test
    @Order(2)
    public void test_exists_not_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("exists materials_format/%d.txt\n", i));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertTrue(std_out.toString().contains("not in database"));
        assertFalse(std_out.toString().contains("is in database"));
    }

    /**
     * Test upload by folder name.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(3)
    public void test_upload() throws IOException {
//        for (int i = 0; i < 10; i++) {
//            sb.append(String.format("upload materials_format/%d.txt\n", i));
//        }
//        sb.append("upload ");
//        for (int i = 10; i < upload_number; i++) {
//            sb.append(String.format("materials_format/%d.txt ", i));
//        }
//        sb.append("\n");
        sb.append("upload ./materials_format/\n");
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertTrue(std_out.toString().contains("is upload database"));
        assertFalse(std_err.toString().contains("happen error"));
    }

    /**
     * Test exists in after files in uploaded, it should recieve file exist response.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(4)
    public void test_exists_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("exists materials_format/%d.txt\n", i));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertTrue(std_out.toString().contains("is in database"));
        assertFalse(std_out.toString().contains("not in database"));
    }

    /**
     * Test upload all exists that use files by files.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(5)
    public void test_upload_all_exists() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("upload materials_format/%d.txt\n", i));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertFalse(std_out.toString().contains("is upload database"));
        assertTrue(std_err.toString().contains("happen error"));
    }

    /**
     * Test compare all file in.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(6)
    public void test_compare_all_file_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("compare " +
                            "materials_format/%d.txt " +
                            "materials_format/%d.txt\n",
                    random.nextInt(upload_number), random.nextInt(upload_number)));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertTrue(std_out.toString().contains("distance between"));
        assertFalse(std_err.toString().contains("Happen error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertFalse(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test compare that 2nd file not in.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(7)
    public void test_compare_2nd_file_not_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("compare " +
                            "materials_format/%d.txt " +
                            "materials_format_not_upload/%d.txt\n",
                    random.nextInt(upload_number), random.nextInt(un_upload_number)));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertFalse(std_out.toString().contains("distance between"));
        assertTrue(std_err.toString().contains("Happen error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertTrue(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test compare that 1st file not in.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(8)
    public void test_compare_1st_file_not_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("compare " +
                            "materials_format_not_upload/%d.txt " +
                            "materials_format/%d.txt\n",
                    random.nextInt(un_upload_number), random.nextInt(upload_number)));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertFalse(std_out.toString().contains("distance between"));
        assertTrue(std_err.toString().contains("Happen error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertTrue(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test compare all file not in.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(9)
    public void test_compare_all_file_not_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("compare " +
                            "materials_format_not_upload/%d.txt " +
                            "materials_format_not_upload/%d.txt\n",
                    random.nextInt(un_upload_number), random.nextInt(un_upload_number)));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertFalse(std_out.toString().contains("distance between"));
        assertTrue(std_err.toString().contains("Happen error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertTrue(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test download all file in.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(10)
    public void test_download_all_file_in() throws IOException {
        for (int i = 0; i < upload_number; i++) {
            sb.append(String.format("download materials_format/%d.txt\n", i));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertTrue(std_out.toString().contains("is download in"));
        assertFalse(std_err.toString().contains("Happen error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertFalse(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test download all file not in.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(11)
    public void test_download_all_file_not_in() throws IOException {
        for (int i = 0; i < un_upload_number; i++) {
            sb.append(String.format("download materials_format_not_upload/%d.txt\n", i));
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        assertFalse(std_out.toString().contains("is download in"));
        assertTrue(std_err.toString().contains("happen error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertTrue(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test list files.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(12)
    public void test_files() throws IOException {
        String inputMessage = "list \n" + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        for (int i = 0; i < upload_number; i++) {
            assertTrue(std_out.toString().contains(String.format("file %d :", i)));
        }
    }

    /**
     * Test upload download file's format.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(13)
    public void test_upload_download_file_format() throws IOException {
        for (File f : new File("./upload/").listFiles()) {
            detector.setText(Files.readAllBytes(f.toPath()));
            CharsetMatch charsetMatch = detector.detect();
            assertEquals("UTF-8", charsetMatch.getName());
        }
        for (File f : new File("./download/").listFiles()) {
            detector.setText(Files.readAllBytes(f.toPath()));
            CharsetMatch charsetMatch = detector.detect();
            assertEquals("UTF-8", charsetMatch.getName());
        }
    }

    /**
     * Test argument number error zero.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(14)
    public void test_argument_number_error_zero() throws IOException {
        sb.append(" \n".repeat(un_upload_number));
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        String temp = std_out.toString();
        assertTrue(std_err.toString().contains("should have parameter"));
        assertFalse(std_err.toString().contains("error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertFalse(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test argument number error one.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(15)
    public void test_argument_number_error_one() throws IOException {
        for (int i = 0; i < un_upload_number; i++) {
            sb.append(UUID.randomUUID().toString()).append(" \n");
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        String temp = std_err.toString();
        assertTrue(std_err.toString().contains("Unknown operation,input once again"));
        assertFalse(std_err.toString().contains("error"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertFalse(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test argument number error one para.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(16)
    public void test_argument_number_error_one_para() throws IOException {
        for (int i = 0; i < un_upload_number; i++) {
            sb.append("exists " + " \n");
            sb.append("upload " + " \n");
            sb.append("download " + " \n");
            sb.append("compare " + " \n");
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        String temp = std_err.toString();
        assertFalse(std_err.toString().contains("Unknown operation,input once again"));
        assertTrue(std_err.toString().contains("parameter should bigger than 1"));
        assertTrue(std_err.toString().contains("parameter should bigger than 2"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertFalse(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
    }

    /**
     * Test argument number error two para.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(17)
    public void test_argument_number_error_two_para() throws IOException {
        for (int i = 0; i < un_upload_number; i++) {
            sb.append("exists ").append(UUID.randomUUID()).append(" \n");
            sb.append("upload ").append(UUID.randomUUID()).append(" \n");
            sb.append("download ").append(UUID.randomUUID()).append(" \n");
            sb.append("compare ").append(UUID.randomUUID()).append(" \n");
        }
        String inputMessage = sb.toString() + "break\n";
        setInput(inputMessage);
        DemoClient.main(new String[0]);
        String temp = std_err.toString();
        assertTrue(std_err.toString().contains("Wrong Path"));
        assertFalse(std_err.toString().contains("Unknown operation,input once again"));
        assertFalse(std_err.toString().contains("parameter should bigger than 1"));
        assertTrue(std_err.toString().contains("parameter should bigger than 2"));
        assertFalse(std_err.toString().contains("error code is 0"));
        assertFalse(std_err.toString().contains("error code is 1"));
        assertFalse(std_err.toString().contains("error code is 2"));
        assertFalse(std_err.toString().contains("error code is 3"));
        assertFalse(std_out.toString().contains("database"));
        assertFalse(std_out.toString().contains("error"));
    }

    /**
     * After each.
     *
     * @throws IOException the io exception
     */
    @AfterEach
    public void after_each() throws IOException {
        assertFalse(std_out.toString().contains("error code is 0"));
        assertFalse(std_out.toString().contains("error code is 1"));
        assertFalse(std_out.toString().contains("error code is 2"));
        assertFalse(std_out.toString().contains("error code is 3"));
        //assertFalse(std_err.toString().contains("database"));
        assertFalse(std_err.toString().contains("download"));
        assertFalse(std_err.toString().contains("length is"));
        assertFalse(std_err.toString().contains("distance between"));
        assertFalse(std_err.toString().contains("upload fail."));
        std_out.close();
        std_err.close();
    }

    private void setInput(String input) {
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
}
