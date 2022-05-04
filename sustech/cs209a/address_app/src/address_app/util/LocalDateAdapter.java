// SPDX-License-Identifier: AGPL-3.0-or-later
package address_app.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

/**
 * Adapter (for JAXB) to convert between the LocalDate and the ISO 8601
 * String representation of the date such as '2012-12-03'.
 *
 * @author Marco Jakob
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDateAdapter() {
    }

    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) {
        return v.toString();
    }
}