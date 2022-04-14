package DocumentPreprocessing;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

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

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LauncherForStudentTest {

    private static Launcher testLauncher;
    @BeforeClass
    public static void init(){
        System.out.println(new File(".").getAbsoluteFile());
        all_flow("test_input_1/config.yml");
    }
    public static void all_flow(String yml_path){
        testLauncher = new Launcher();
        testLauncher.parseConfigFile(yml_path);
        testLauncher.readInputCsv();
        testLauncher.processFiles();
        testLauncher.writeSummary();
    }
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void test01_config_default_value() {
        Launcher launcher = new Launcher();
        launcher.parseConfigFile("test_input_1/config_default_value.yml");

        assertTrue(
                "the default value of prefix should be null or empty string",
                launcher.prefix == null || launcher.prefix.isEmpty()
        );
        assertTrue(
                "the default value of postfix should be null or empty string",
                launcher.postfix == null || launcher.postfix.isEmpty()
        );
        assertEquals(
                "the default value of targetEncoding should be UTF-8",
                "UTF-8", launcher.targetEncoding.toUpperCase()
        );
        assertTrue(
                "the default value of categorizeCriteriaList should be null or empty list",
                launcher.categorizeCriteriaList == null || launcher.categorizeCriteriaList.isEmpty()
        );
    }

    @Test
    public void test02_config_no_path_csv() {
        exceptionRule.expect(NullPointerException.class);
        all_flow("test_input_1/config_no_path_csv.yml");
    }
    @Test
    public void test02_config_no_path_output() {
        exceptionRule.expect(NullPointerException.class);
        all_flow("test_input_1/config_no_path_output.yml");
    }
    @Test
    public void test02_config_no_path_txt() {
        exceptionRule.expect(NullPointerException.class);
        all_flow("test_input_1/config_no_path_txt.yml");
    }
    @Test
    public void test03_config_illegal_path_csv() {
        exceptionRule.expect(IllegalArgumentException.class);
        all_flow("test_input_1/config_illegal_path_csv.yml");
    }

    @Test
    public void test03_config_illegal_path_output() {
        exceptionRule.expect(IllegalArgumentException.class);
        all_flow("test_input_1/config_illegal_path_output.yml");
    }

    @Test
    public void test03_config_illegal_path_txt() {
        exceptionRule.expect(IllegalArgumentException.class);
        all_flow("test_input_1/config_illegal_path_txt.yml");
    }

    @Test
    public void test04_config_no_CategorizeBy(){
        all_flow("test_input_1/config_no_CategorizeBy.yml");
        File file = new File(String.valueOf(Paths.get("./test_output_2")));
        for (String s: Objects.requireNonNull(file.list())){
            assertTrue(new File(String.valueOf(Paths.get("./test_output_2"+File.separator+s))).isFile());
            assertFalse(new File(String.valueOf(Paths.get("./test_output_2"+File.separator+s))).isDirectory());
        }
    }
    @Test
    public void test05_csv_wrong_include(){
        exceptionRule.expect(IllegalArgumentException.class);
        all_flow("test_input_1/config_csv_wrong_include.yml");
    }
    @Test
    public void test10_config() throws IOException {
        all_flow("test_input_1/config.yml");
        assertTrue(
                "the inputCsvPath should be input.csv",
                Files.isSameFile(
                        new File("test_input_1/input.csv").toPath(),
                        new File(testLauncher.inputCsvPath).toPath()
                )
        );
        assertTrue(
                "the inputTxtPath should be indata",
                Files.isSameFile(
                        new File("test_input_1/indata").toPath(),
                        new File(testLauncher.inputTxtPath).toPath()
                )
        );
        assertTrue(
                "the summaryPath should be newout.csv",
                Files.isSameFile(
                        new File("test_output_1/newout.csv").toPath(),
                        new File(testLauncher.summaryPath).toPath()
                )
        );
        ArrayList<CategorizeCriteria> list = new ArrayList<>();
        list.add(CategorizeCriteria.FIELD);
        list.add(CategorizeCriteria.SOURCE);
        list.add(CategorizeCriteria.DATE);

        assertEquals(
                "the categorizeCriteriaList should be [ Field, Source, Date ]",
                list, testLauncher.categorizeCriteriaList
        );
    }

    @Test
    public void test11_input_csv() {
        List<FileEntry> res = testLauncher.fileEntries;
        assertEquals(
                "the size of fileEntries should be 15",
                18, res.size()
        );
        for (FileEntry fileEntry : res) {
            assertTrue(
                    "the input file should exist",
                    fileEntry.file.exists()
            );
        }
        Optional<FileEntry> optionalFirst = res.stream().filter((it) ->
                it.file.getName().equals("6.txt")
        ).findAny();
        assertTrue("should exist 6.txt", optionalFirst.isPresent());

        FileEntry first = optionalFirst.get();
        assertEquals("check the source of input", "南科大", first.source);
        assertEquals("check the field of input", "书院新闻", first.field);
        assertEquals(
                "check the date of input",
                "2015-11-18", first.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    @Test
    public void test12_file_info() {
        List<FileEntry> res = testLauncher.fileEntries;
        for (FileEntry fileEntry : res) {
            File outputFile = new File(fileEntry.newPath);
            assertTrue(
                    "output file should exist",
                    outputFile.exists()
            );
            assertTrue(
                    "output file should contains prefix and postfix",
                    outputFile.getName().matches("sustech_\\d+_cs.txt")
            );
        }
    }

    @Test
    public void test13_dir_struct() {
        List<FileEntry> res = testLauncher.fileEntries;
        Path path = new File("./test_output_1").toPath();
        assertTrue(
                "check dir struct",
                path.resolve("专题报道").resolve("南科大").resolve("半年以上").toFile().exists()
        );
        assertTrue(
                "check dir struct",
                path.resolve("教学新闻").resolve("南科大").resolve("半年内").toFile().exists()
        );
    }

    @Test
    public void test14_file_encoding() throws IOException {
        List<FileEntry> res = testLauncher.fileEntries;
        assertEquals(
                "check file encoding",
                readString("test_output_1/科研新闻/南科大/半年以上/sustech_5_cs.txt", StandardCharsets.UTF_8),
                readString("test_input_1/indata/5.txt", Charset.forName("GB18030"))
        );
        assertEquals(
                "check file encoding",
                readString("test_output_1/科研新闻/南科大/半年以上/sustech_10_cs.txt", StandardCharsets.UTF_8),
                readString("test_input_1/indata/10.txt", StandardCharsets.UTF_16LE)
        );
    }

    @Test
    public void test15_summary() throws IOException {
        List<String> result = Files.readAllLines(new File("test_output_1/newout.csv").toPath(), StandardCharsets.UTF_8);
        for (String s : result) {
            if (s.contains("sustech_6_cs.txt")) {
                String[] spilt = s.split(",");
                assertEquals(
                        "check source of summary",
                        "南科大", spilt[1]
                );
                assertEquals(
                        "check field of summary",
                        "书院新闻", spilt[2]
                );
                assertEquals(
                        "check date of summary",
                        "20151118", spilt[3]
                );
                assertEquals(
                        "check char_count of summary",
                        String.valueOf(readString("test_input_1/indata/6.txt", Charset.forName("GB18030")).length()),
                        spilt[4]
                );
                assertEquals(
                        "check old_encoding of summary",
                        "GB18030", spilt[5]
                );
            }
            if (s.contains("sustech_17_cs.txt")) {
                String[] spilt = s.split(",");
                assertEquals(
                        "check source of summary",
                        "文匯報", spilt[1]
                );
                assertEquals(
                        "check field of summary",
                        "专题报道", spilt[2]
                );
                assertEquals(
                        "check date of summary",
                        "20200301", spilt[3]
                );
                assertEquals(
                        "check char_count of summary",
                        String.valueOf(readString("test_input_1/indata/17.txt", StandardCharsets.UTF_8).length()),
                        spilt[4]
                );
                assertEquals(
                        "check old_encoding of summary",
                        "UTF-8", spilt[5]
                );
            }
        }
    }

    private static String readString(String pathname, Charset utf8) throws IOException {
        return new String(Files.readAllBytes(new File(pathname).toPath()), utf8);
    }

}

