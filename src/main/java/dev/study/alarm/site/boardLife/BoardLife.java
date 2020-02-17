package dev.study.alarm.site.boardLife;

import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.site.Site;
import dev.study.alarm.utill.JdbcUtil;

import java.util.List;


public class BoardLife extends Site {

    private BoardLife(Messenger messenger, JdbcUtil jdbcUtil, List<BoardLifeSelector> selectors) {
        super(messenger, jdbcUtil, selectors);
    }

    public static BoardLife of(Messenger messenger, JdbcUtil jdbcUtil, List<BoardLifeSelector> selectors) {
        return new BoardLife(messenger, jdbcUtil, selectors);
    }
}
