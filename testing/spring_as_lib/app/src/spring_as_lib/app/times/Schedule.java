package spring_as_lib.app.times;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring_as_lib.elements.element;

import java.util.concurrent.TimeUnit;

@Log
@Service
public class Schedule {
    private final element e;

    public Schedule(@Autowired element e) {
        this.e = e;
    }

    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.SECONDS)
    public void print() {
        log.info(e.request());
    }
}
