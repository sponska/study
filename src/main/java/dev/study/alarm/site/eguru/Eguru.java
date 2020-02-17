package dev.study.alarm.site.eguru;

import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.site.Site;
import dev.study.alarm.utill.JdbcUtil;

import java.util.List;

public class Eguru extends Site {

    private Eguru(Messenger messenger, JdbcUtil jdbcUtil, List<EguruSelector> selectors) {
        super(messenger, jdbcUtil, selectors);
    }

    public static Eguru of(Messenger messenger, JdbcUtil jdbcUtil, List<EguruSelector> selectors) {
        return new Eguru(messenger, jdbcUtil, selectors);
    }
}
