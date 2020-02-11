package dev.study.alarm;


import com.google.common.base.Charsets;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncodedUtilsTest {

    @Test
    public void parseTest() throws UnsupportedEncodingException {
        String actual = URLEncoder.encode("크레이지 타임", Charsets.UTF_8.name());
        String expected = "%C5%A9%B7%B9%C0%CC%C1%F6+%C5%B8%C0%D3";
        Assert.assertEquals(expected, actual);
    }
}
