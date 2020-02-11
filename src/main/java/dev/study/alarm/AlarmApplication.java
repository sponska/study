package dev.study.alarm;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
@PropertySource("/slack.properties")
public class AlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlarmApplication.class, args);
    }
}
