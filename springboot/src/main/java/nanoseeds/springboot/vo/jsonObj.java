// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.vo;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public final class jsonObj extends base {
    private final String str1;
    private final String str2;
    private final Long l1;
    private final Double d1;
    private final long l2;
    private final double d2;
    private static final String strStatic1 = "DO_NOT_SERIZED_IN_JSON";

    private jsonObj() {
        throw re;
    }

    public static jsonObj of() {
        return new jsonObj("", "", 1L, 2.0d, 3L, 4.0d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        final jsonObj jsonObj = (jsonObj) o;
        return l2 == jsonObj.l2 && Double.compare(jsonObj.d2, d2) == 0 && Objects.equal(str1, jsonObj.str1)
                && Objects.equal(str2, jsonObj.str2) && Objects.equal(l1, jsonObj.l1) && Objects.equal(d1, jsonObj.d1);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(str1, str2, l1, d1, l2, d2);
    }
}
