package DocumentPreprocessing;

import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Examples {
    // here are some examples to help you familiar with some tools
    public static void main(String[] args) {
        detectEncodingAndRead();
        dateFormats();
    }

    public static void detectEncodingAndRead(){
        File file = new File("indata/5.txt");
        CharsetDetector detector = new CharsetDetector();
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            detector.setText(bytes);
            CharsetMatch charsetMatch = detector.detect();
            String encoding = charsetMatch.getName();
            String content = charsetMatch.getString();
            System.out.println("Detected encoding is: "+ encoding);
            System.out.println("Content of file is :"+content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dateFormats(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        LocalDate parsedDate = LocalDate.parse("2020/3/6",dateTimeFormatter);
        System.out.println("Parsed date: "+parsedDate);

        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println("Formatted date:"+outputFormat.format(parsedDate));
    }
}
