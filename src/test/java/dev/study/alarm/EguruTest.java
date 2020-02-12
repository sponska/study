package dev.study.alarm;

import dev.study.alarm.site.Eguru;
import dev.study.alarm.site.Site;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Comparator;

public class EguruTest {

    @Test
    public void test() throws IOException {


        Site site = new Eguru("kingdom");

        Document document = site.getDocument();

        Elements itemList = site.getItemList(document, "a[target=_blank]");

        String actual = itemList.stream()
                .filter(element -> element.text()
                        .contains("화"))
                .max(Comparator.comparingInt(o -> Integer.parseInt(o.text()
                        .substring(3, o.text()
                                              .length() - 1))))
                .map(Element::text).orElse("");

        Assert.assertEquals("킹덤 631화", actual);

    }

}
