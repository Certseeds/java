// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.vo;

import lombok.extern.slf4j.Slf4j;
import nanoseeds.springboot.service.logService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class testRunner extends base implements Runnable {
    private final Thread thread;

    testRunner() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        log.info("log.env is");
        logService.log();
    }
}
