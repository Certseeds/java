package DocumentPreprocessing;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum CategorizeCriteria {
    SOURCE {
        @Override
        String toCategoryKey(FileEntry e) {
            return e.source;
        }
    }, FIELD {
        @Override
        String toCategoryKey(FileEntry e) {
            return e.field;
        }
    }, DATE {
        @Override
        String toCategoryKey(FileEntry e) {
            final LocalDate today = LocalDate.now();
            final long days = e.date.until(today, ChronoUnit.DAYS);
            if (days <= 7) return "一周内";
            else if (days <= 30) return "一月内";
            else if (days <= 180) return "半年内";
            else return "半年以上";
        }
    };

    String toCategoryKey(FileEntry e) {
        throw new AbstractMethodError();
    }
}