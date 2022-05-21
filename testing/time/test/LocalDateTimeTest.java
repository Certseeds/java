import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

@Log
public class LocalDateTimeTest {
    @Test
    public void test1() {
        final var t1 = LocalDateTime.now();
        final var date1 = t1.toLocalDate();
        final var time1 = t1.toLocalTime();
        // print obey ISO-8601
        log.info(t1.toString());
        // 打印的精度很高
        log.info(date1.toString());
        // 只有日期
        log.info(time1.toString());
        // 时间精度非常高
    }

    @Test
    public void test2() {
        final var dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.SIMPLIFIED_CHINESE);
        log.info(dtf.format(LocalDateTime.now()));
        final var dtfB = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4, 4, SignStyle.NORMAL)
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .toFormatter(Locale.ENGLISH);
        // 这样描述起来很精确
        log.info(dtfB.format(LocalDateTime.now()));
        final var now = LocalDateTime.now();
        Assertions.assertEquals(
                dtf.format(now)
                , dtfB.format(now)
        );
        // 在表达数字方面,Locale.SIMPLIFIED_CHINESE, Locale.English确实没区别
    }


}
