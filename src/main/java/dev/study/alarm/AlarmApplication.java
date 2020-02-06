package dev.study.alarm;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableEncryptableProperties
@PropertySource("/slack.properties")
public class AlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlarmApplication.class, args);
    }

    @Component
    public class MyRunner implements CommandLineRunner {

        @Value("${spring.batch.job.names}")
        private String myProperty;

        @Override
        public void run(String... args) throws Exception {
        }

    }
}
