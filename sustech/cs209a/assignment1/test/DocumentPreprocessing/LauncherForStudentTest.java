package DocumentPreprocessing;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@TestMethodOrder(MethodOrderer.MethodName.class)
public class LauncherForStudentTest {

    private static Launcher testLauncher;

    @BeforeAll
    public static void init() {
        System.out.println(new File(".").getAbsoluteFile());
        all_flow("test_input_1/config.yml");
    }

    public static void all_flow(String yml_path) {
        testLauncher = new Launcher();
        testLauncher.parseConfigFile(yml_path);
        testLauncher.readInputCsv();
        testLauncher.processFiles();
        testLauncher.writeSummary();
    }

    @Test
    public void test01_config_default_value() {
        Launcher launcher = new Launcher();
        launcher.parseConfigFile("test_input_1/config_default_value.yml");

        Assertions.assertTrue(
                launcher.prefix == null || launcher.prefix.isEmpty(),
                "the default value of prefix should be null or empty string"

        );
        Assertions.assertTrue(
                launcher.postfix == null || launcher.postfix.isEmpty(),
                "the default value of postfix should be null or empty string"
        );
        Assertions.assertEquals(
                "UTF-8", launcher.targetEncoding.toUpperCase(),
                "the default value of targetEncoding should be UTF-8"
        );
        Assertions.assertTrue(
                launcher.categorizeCriteriaList == null || launcher.categorizeCriteriaList.isEmpty(),
                "the default value of categorizeCriteriaList should be null or empty list"
        );
    }

    @Test
    public void test02_config_no_path_csv() {
        Assertions.assertThrows(NullPointerException.class,
                () -> all_flow("test_input_1/config_no_path_csv.yml")
        );
    }

    @Test
    public void test02_config_no_path_output() {
        Assertions.assertThrows(NullPointerException.class,
                () -> all_flow("test_input_1/config_no_path_output.yml")
        );
    }

    @Test
    public void test02_config_no_path_txt() {
        Assertions.assertThrows(NullPointerException.class,
                () -> all_flow("test_input_1/config_no_path_txt.yml")
        );
    }

    @Test
    public void test03_config_illegal_path_csv() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> all_flow("test_input_1/config_illegal_path_csv.yml")
        );
    }

    @Test
    public void test03_config_illegal_path_output() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> all_flow("test_input_1/config_illegal_path_output.yml")
        );
    }

    @Test
    public void test03_config_illegal_path_txt() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> all_flow("test_input_1/config_illegal_path_txt.yml")
        );
    }

    @Test
    public void test04_config_no_CategorizeBy() {
        all_flow("test_input_1/config_no_CategorizeBy.yml");
        File file = new File(String.valueOf(Paths.get("./test_output_2")));
        for (String s : Objects.requireNonNull(file.list())) {
            Assertions.assertTrue(new File(String.valueOf(Paths.get("./test_output_2" + File.separator + s))).isFile());
            Assertions.assertFalse(new File(String.valueOf(Paths.get("./test_output_2" + File.separator + s))).isDirectory());
        }
    }

    @Test
    public void test05_csv_wrong_include() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> all_flow("test_input_1/config_csv_wrong_include.yml")
        );
    }

    @Test
    public void test10_config() throws IOException {
        all_flow("test_input_1/config.yml");
        Assertions.assertTrue(
                Files.isSameFile(
                        new File("test_input_1/input.csv").toPath(),
                        new File(testLauncher.inputCsvPath).toPath()
                ),
                "the inputCsvPath should be input.csv"
        );
        Assertions.assertTrue(
                Files.isSameFile(
                        new File("test_input_1/indata").toPath(),
                        new File(testLauncher.inputTxtPath).toPath()
                ),
                "the inputTxtPath should be indata"
        );
        Assertions.assertTrue(
                Files.isSameFile(
                        new File("test_output_1/newout.csv").toPath(),
                        new File(testLauncher.summaryPath).toPath()
                ),
                "the summaryPath should be newout.csv"
        );
        ArrayList<CategorizeCriteria> list = new ArrayList<>();
        list.add(CategorizeCriteria.FIELD);
        list.add(CategorizeCriteria.SOURCE);
        list.add(CategorizeCriteria.DATE);

        Assertions.assertEquals(
                list,
                testLauncher.categorizeCriteriaList, "the categorizeCriteriaList should be [ Field, Source, Date ]"
        );
    }

    @Test
    public void test11_input_csv() {
        List<FileEntry> res = testLauncher.fileEntries;
        Assertions.assertEquals(
                18, res.size(), "the size of fileEntries should be 18"
        );
        for (FileEntry fileEntry : res) {
            Assertions.assertTrue(
                    fileEntry.file.exists(),
                    "the input file should exist"
            );
        }
        Optional<FileEntry> optionalFirst = res.stream().filter((it) ->
                it.file.getName().equals("6.txt")
        ).findAny();
        Assertions.assertTrue(optionalFirst.isPresent(), "should exist 6.txt");

        FileEntry first = optionalFirst.get();
        Assertions.assertEquals("南科大", first.source, "check the source of input");
        Assertions.assertEquals("书院新闻", first.field, "check the field of input");
        Assertions.assertEquals("2015-11-18", first.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "check the date of input"
        );
    }

    @Test
    public void test12_file_info() {
        List<FileEntry> res = testLauncher.fileEntries;
        for (FileEntry fileEntry : res) {
            File outputFile = new File(fileEntry.newPath);
            Assertions.assertTrue(
                    outputFile.exists(),
                    "output file should exist"
            );
            Assertions.assertTrue(
                    outputFile.getName().matches("sustech_\\d+_cs.txt"),
                    "output file should contains prefix and postfix"
            );
        }
    }

    @Test
    public void test13_dir_struct() {
        List<FileEntry> res = testLauncher.fileEntries;
        Path path = new File("./test_output_1").toPath();
        Assertions.assertTrue(
                path.resolve("专题报道").resolve("南科大").resolve("半年以上").toFile().exists(),
                "check dir struct"
        );
        Assertions.assertTrue(
                path.resolve("教学新闻").resolve("南科大").resolve("半年内").toFile().exists(),
                "check dir struct"
        );
    }

    @Test
    public void test14_file_encoding() throws IOException {
        List<FileEntry> res = testLauncher.fileEntries;
        Assertions.assertEquals(
                readString("test_output_1/科研新闻/南科大/半年以上/sustech_5_cs.txt", StandardCharsets.UTF_8),
                readString("test_input_1/indata/5.txt", Charset.forName("GB18030")),
                "check file encoding"
        );
        Assertions.assertEquals(
                readString("test_output_1/科研新闻/南科大/半年以上/sustech_10_cs.txt", StandardCharsets.UTF_8),
                readString("test_input_1/indata/10.txt", StandardCharsets.UTF_16LE),
                "check file encoding"
        );
    }

    @Test
    public void test15_summary() throws IOException {
        final List<String> result = Files.readAllLines(new File("test_output_1/newout.csv").toPath(), StandardCharsets.UTF_8);
        for (String s : result) {
            if (s.contains("sustech_6_cs.txt")) {
                final String[] spilt = s.split(",");
                Assertions.assertEquals(
                        "南科大", spilt[1],
                        "check source of summary"
                );
                Assertions.assertEquals(
                        "书院新闻", spilt[2],
                        "check field of summary"
                );
                Assertions.assertEquals(
                        "20151118", spilt[3],
                        "check date of summary"
                );
                Assertions.assertEquals(
                        String.valueOf(readString("test_input_1/indata/6.txt", Charset.forName("GB18030")).length()),
                        spilt[4],
                        "check char_count of summary"
                );
                Assertions.assertEquals(
                        "GB18030", spilt[5],
                        "check old_encoding of summary");
            }
            if (s.contains("sustech_17_cs.txt")) {
                String[] spilt = s.split(",");
                Assertions.assertEquals(
                        "文匯報", spilt[1],
                        "check source of summary"
                );
                Assertions.assertEquals(
                        "专题报道", spilt[2],
                        "check field of summary"
                );
                Assertions.assertEquals(
                        "20200301", spilt[3],
                        "check date of summary"
                );
                Assertions.assertEquals(
                        String.valueOf(readString("test_input_1/indata/17.txt", StandardCharsets.UTF_8).length()),
                        spilt[4],
                        "check char_count of summary"
                );
                Assertions.assertEquals(
                        "UTF-8", spilt[5],
                        "check old_encoding of summary"
                );
            }
        }
    }

    private static String readString(String pathname, Charset charset) throws IOException {
        return new String(Files.readAllBytes(new File(pathname).toPath()), charset);
    }

}

