package DocumentPreprocessing;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class FileEntry {
    File file;
    String source;
    String field;
    LocalDate date;
    String encoding;
    int length;
    String newPath;

    public FileEntry(File file, String source, String field, LocalDate date, String encoding) {
        this.file = file;
        this.source = source;
        this.field = field;
        this.date = date;
        this.encoding = encoding;
        this.length = 0;
        this.newPath = null;
    }

    public String toSummaryString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        return newPath + "," + source + "," + field + "," + outputFormat.format(date) + "," + length + "," + encoding;
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "file=" + file +
                ", source='" + source + '\'' +
                ", field='" + field + '\'' +
                ", date=" + date +
                ", encoding='" + encoding + '\'' +
                ", length=" + length +
                ", newPath='" + newPath + '\'' +
                '}';
    }

    public FileEntry() {}

}
