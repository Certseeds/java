import com.google.common.collect.ImmutableMap;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log
public class BaseTest {
    private static final int length = 1024000;
    //TODO
    @Test
    public void test1() {
        log.info(String.valueOf(System.currentTimeMillis()));
        final var hashMap = new HashMap<String, String>();
        final var builder = new ImmutableMap.Builder<String, String>();
        for (int i = 0; i < length; ++i) {
            hashMap.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
            builder.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        }
        final var immutableMap = builder.build();
        final var jdkImmutableMap = Map.ofEntries(hashMap.entrySet().toArray(new Map.Entry[0]));
        Assertions.assertTrue(true);
    }

}
