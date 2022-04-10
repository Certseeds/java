// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.vo;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;

public class base {
    protected static final RuntimeException re = new RuntimeException("should not use default init func");

    protected base() {}

    protected static base of() {
        return new base();
    }

    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Deprecated
    protected final void finalize() {}

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("can't deserialize");
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("can't deserialize");
    }
}
