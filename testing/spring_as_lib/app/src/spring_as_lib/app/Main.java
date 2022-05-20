package spring_as_lib.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(scanBasePackages = "spring_as_lib")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
