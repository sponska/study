package dev.study.alarm.utill;

import com.sun.deploy.security.JarSignature;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JdbcUtil {

    private final JdbcTemplate jdbcTemplate;

    public void updateTitle(String title, String keyword) {
        jdbcTemplate.update("UPDATE OLD_ITEM SET TITLE = ? WHERE KEYWORD = ?", title, keyword);
    }

    public String getOldTitle(String keyword) {
        try {
            return jdbcTemplate.queryForObject("SELECT TITLE FROM OLD_ITEM WHERE KEYWORD = ?", new String[]{keyword}, String.class);
        } catch (Exception e) {
            jdbcTemplate.update("INSERT INTO OLD_ITEM (KEYWORD,TITLE) VALUES(?,?)",keyword,"");
            return null;
        }
    }

}
