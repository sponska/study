package dev.study.alarm.site;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class TopItem {
    String title;
    String link;

    public String getText(){
        return this.title + " \n " + link;
    }

    public String getTitle() {
        return this.title;
    }
}
