package dev.study.alarm.site;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class BoardLifeTest {

    private Element topItemInfo;
    private String title;
    private String linkUrl;
    private String baseUrl;
    private Site site;

    @BeforeEach
    void setUp() {
        baseUrl = "http://boardlife.co.kr";
        site = spy((Site) new BoardLife("크레이지 타임"));
        getMockTopItemInfo();
    }

    @Test
    void getTopItemInfo() throws IOException {
        Document mockDocument = getMockDocument();
        when(site.getDocument()).thenReturn(mockDocument);

        Element actual = site.getTopItemInfo();
        Element expected = getTopItemInfoExpected();

        assertThat(actual.toString()).isEqualTo(expected.toString());
    }

    @Test
    void getTitle() throws IOException {
        when(site.getTopItemInfo()).thenReturn(topItemInfo);
        String actual = site.getTitle();
        assertThat(actual).isEqualTo(title);
    }

    @Test
    void getLink() throws IOException {
        when(site.getTopItemInfo()).thenReturn(topItemInfo);
        String actual = site.getLink();

        String expected = baseUrl + "/" + linkUrl;
        assertThat(actual).isEqualTo(expected);
    }

    private Document getMockDocument() {
        String html = "<table><tbody><tr onmouseover=\"this.style.backgroundColor='#ffffff'\" onmouseout=\"this.style.backgroundColor=''\" style=\"\"><td width=\"1\"></td><td height=\"35\" width=\"50\" align=\"center\">119</td><td width=\"1\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"height:30px; \">\n" +
                      "\t<tbody><tr>\n" +
                      "\t\t<td style=\"color:#e6e6e6;\">|</td>\n" +
                      "\t</tr>\n" +
                      "</tbody></table></td><td height=\"35\" width=\"100\" align=\"center\"><img src=\"img/판매.png\"></td><td width=\"1\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"height:30px; \">\n" +
                      "\t<tbody><tr>\n" +
                      "\t\t<td style=\"color:#e6e6e6;\">|</td>\n" +
                      "\t</tr>\n" +
                      "</tbody></table></td><td height=\"35\" width=\"499\" align=\"left\"><a href=\"./bbs_detail.php?bbs_num=19508&amp;tb=board_used&amp;id=&amp;num=&amp;pg=&amp;game_id=&amp;start=&amp;b_category=&amp;game_category=\" target=\"_self\">황혼의투쟁 외 다수 게임 팔고 겜블러 외 다수 삽니다.(교환은 문의) <img src=\"upload/happy_select_icon/default_new_icon-1318833247897812.gif\" style=\"margin-left:3px;\"> <span style=\"font-weight:bold; font-size:11px;\">[10]</span></a></td><td width=\"1\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"height:30px; \">\n" +
                      "\t<tbody><tr>\n" +
                      "\t\t<td style=\"color:#e6e6e6;\">|</td>\n" +
                      "\t</tr>\n" +
                      "</tbody></table></td><td height=\"35\" width=\"100\" align=\"center\"><a href=\"blog.php?uid=kaname92\">행정부</a></td><td width=\"1\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"height:30px; \">\n" +
                      "\t<tbody><tr>\n" +
                      "\t\t<td style=\"color:#e6e6e6;\">|</td>\n" +
                      "\t</tr>\n" +
                      "</tbody></table></td><td height=\"35\" width=\"100\" align=\"center\">2020-02-13</td><td width=\"1\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"height:30px; \">\n" +
                      "\t<tbody><tr>\n" +
                      "\t\t<td style=\"color:#e6e6e6;\">|</td>\n" +
                      "\t</tr>\n" +
                      "</tbody></table></td><td height=\"35\" width=\"50\" align=\"center\">119</td><td width=\"1\"></td></tr></tbody></table>";
        Document document = new Document(baseUrl);
        document.html(html);
        return document;
    }

    private void getMockTopItemInfo() {
        linkUrl = "bbs_detail.php?bbs_num=19508&tb=board_used&id=&num=&pg=&game_id=&start=&b_category=&game_category=";
        title = "황혼의투쟁 외 다수 게임 팔고 겜블러 외 다수 삽니다.(교환은 문의) ";
        topItemInfo = new Element("a");
        topItemInfo.attr("href", "." + linkUrl);
        String html = title +
                      "<img src=\"upload/happy_select_icon/default_new_icon-1318833247897812.gif\" style=\"margin-left:3px;\"> " +
                      "<span style=\"font-weight:bold; font-size:11px;\">[10]</span>";
        topItemInfo.html(html);
    }

    private Element getTopItemInfoExpected() {
        Element expected = new Element("a");
        expected.attr("href", "./bbs_detail.php?bbs_num=19508&tb=board_used&id=&num=&pg=&game_id=&start=&b_category=&game_category=");
        expected.attr("target", "_self");
        expected.html("황혼의투쟁 외 다수 게임 팔고 겜블러 외 다수 삽니다.(교환은 문의) <img src=\"upload/happy_select_icon/default_new_icon-1318833247897812.gif\" style=\"margin-left:3px;\"> <span style=\"font-weight:bold; font-size:11px;\">[10]</span>");
        return expected;
    }
}