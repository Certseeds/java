import lombok.extern.java.Log;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Log
public class BaseTest {

    @Test
    public void test1() {
        log.info(String.valueOf(System.currentTimeMillis()));
    }

    @Test
    public void test2() {
        final long mills = System.currentTimeMillis();
        log.info(java.time.ZonedDateTime.
                from(Instant.ofEpochMilli(mills).atOffset(ZoneOffset.ofHours(8)))
                .format(DateTimeFormatter.RFC_1123_DATE_TIME)
        );
        log.info(java.time.ZonedDateTime.
                from(Instant.ofEpochMilli(mills).atOffset(ZoneOffset.ofHours(8)))
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        final String normalFormat = "yyyy-MM-dd HH:mm:ss";
        log.info(java.time.ZonedDateTime
                .from(
                        Instant.ofEpochMilli(mills)
                                .atOffset(ZoneOffset.ofHours(8))
                )
                .format(DateTimeFormatter.ofPattern(normalFormat))
        );
        Assertions.assertEquals(
                java.time.ZonedDateTime
                        .from(Instant.ofEpochMilli(mills).atOffset(ZoneOffset.ofHours(8)))
                        .format(DateTimeFormatter.ofPattern(normalFormat)),
                org.joda.time.format.DateTimeFormat.forPattern(normalFormat)
                        .print(new org.joda.time.DateTime(mills)
                                .withZone(DateTimeZone.forOffsetHours(8))
                        )
        );
    }

    @Test
    public void test3() {
        final long mills = System.currentTimeMillis();
        final long javaTimeThree = java.time.LocalDate.from(Instant.ofEpochMilli(mills).atOffset(ZoneOffset.ofHours(8)))
                .atStartOfDay()
                .minusDays(2)
                .plusHours(3)
                .toInstant(ZoneOffset.ofHours(8))
                .toEpochMilli();
        log.info(String.valueOf(javaTimeThree));
        Assertions.assertEquals(
                javaTimeThree,
                new org.joda.time.DateTime(mills).withZone(org.joda.time.DateTimeZone.forOffsetHours(8))
                        .withTimeAtStartOfDay()
                        .minusDays(2)
                        .plusHours(3)
                        .toInstant()
                        .getMillis()
        );
    }

    @Test
    public void test4() {
        final String timeFormat = "yyyy-MM-dd HH:mm:ss";
        final java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern(timeFormat);
        final long java8UTC0 = java.time.LocalDateTime.parse("2022-02-22 22:22:22", dtf)
                .atOffset(ZoneOffset.ofHours(0))
                .toInstant()
                .toEpochMilli();
        final long java8UTC8 = java.time.LocalDateTime.parse("2022-02-22 22:22:22", dtf)
                .atOffset(ZoneOffset.ofHours(8))
                .toInstant()
                .toEpochMilli();
        log.info(String.valueOf(java8UTC0));
        log.info(String.valueOf(java8UTC8));
        Assertions.assertEquals(
                java8UTC8,
                org.joda.time.LocalDateTime.parse(
                                "2022-02-22 22:22:22",
                                org.joda.time.format.DateTimeFormat.forPattern(timeFormat)
                        ).toDateTime()
                        .withZone(DateTimeZone.forOffsetHours(8))
                        .toInstant()
                        .getMillis()
        );
        Assertions.assertEquals(
                java8UTC0,
                org.joda.time.LocalDateTime.parse(
                                "2022-02-22 22:22:22",
                                org.joda.time.format.DateTimeFormat.forPattern(timeFormat)
                        )
                        .toDateTime(DateTimeZone.forOffsetHours(0))
                        .toInstant()
                        .getMillis()
        );
    }
}
