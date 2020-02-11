package dev.study.alarm.db;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class H2Runner implements ApplicationRunner {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection);
            String URL = connection.getMetaData()
                    .getURL();
            System.out.println(URL);
            String User = connection.getMetaData()
                    .getUserName();
            System.out.println(User);

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE OLD_ITEM(ID INTEGER AUTO_INCREMENT PRIMARY KEY ,KEYWORD VARCHAR(255),TITLE VARCHAR(255))";
            statement.executeUpdate(sql);
        }
    }
}