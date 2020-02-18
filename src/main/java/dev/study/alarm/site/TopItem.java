package dev.study.alarm.site;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class TopItem {

    String title;
    String link;
    String keyword;

    public String getText() {
        return this.title + " \n " + link;
    }

    public String getTitle() {
        return this.title;
    }

    public String getKeyword() {
        return this.keyword;
    }
}
