// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class logService {
    public static void log() {
        log.info("hello, world");
    }

    public static Logger get() {
        return log;
    }
}
