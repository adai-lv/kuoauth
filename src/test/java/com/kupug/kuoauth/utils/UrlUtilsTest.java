package com.kupug.kuoauth.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UrlUtilsTest {

    @Test
    public void urlParamEncode() {

        String paramValue = "cn#j+k*2 /s~e";
        String actual = UrlUtils.urlEncode(paramValue);

        String expected = "cn%23j%2Bk%2A2%20%2Fs%7Ee";

        Assert.assertEquals("UrlUtilsTest.urlParamEncode", expected, actual);
    }

    @Test
    public void urlEncode() {

        String url = "http://www.yabale.com/index.html?language=cnj+k*2s~e#j2se";
        String actual = UrlUtils.urlEncode(url);

        String expected = "http%3A%2F%2Fwww.yabale.com%2Findex.html%3Flanguage%3Dcnj%2Bk%2A2s%7Ee%23j2se";

        Assert.assertEquals("UrlUtilsTest.urlEncode", expected, actual);
    }

    @Test
    public void urlDecode() {

        String url = "http%3A%2F%2Fwww.yabale.com%2Findex.html%3Flanguage%3Dcnj%2Bk%2A2s%7Ee%23j2se";
        String actual = UrlUtils.urlDecode(url);

        String expected = "http://www.yabale.com/index.html?language=cnj+k*2s~e#j2se";

        Assert.assertEquals("UrlUtilsTest.urlDecode", expected, actual);
    }

    @Test
    public void parseMapToString() {

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(1));
        params.put("boy", String.valueOf(true));
        params.put("email", "xxx@xxx.com");
        params.put("phone", "1234****345");

        String unEncodeActual = UrlUtils.valueOf(params);
        String unEncodeExpected = "phone=1234****345&id=1&boy=true&email=xxx@xxx.com";
        Assert.assertEquals("UrlUtils.parseMapToString", unEncodeExpected, unEncodeActual);

        String encodeActual = UrlUtils.valueOf(params, true);
        String encodeExpected = "phone=1234%2A%2A%2A%2A345&id=1&boy=true&email=xxx%40xxx.com";
        Assert.assertEquals("UrlUtils.parseMapToString", encodeExpected, encodeActual);
    }

    @Test
    public void parseStringToMap() {

        String paramsStr = "phone=1234%2A%2A%2A%2A345&id=1&boy=true&email=xxx%40xxx.com";

        Map<String, String> unDecodeActual = UrlUtils.valueOf(paramsStr);
        String unDecodeExpected = "{\"phone\":\"1234%2A%2A%2A%2A345\",\"id\":\"1\",\"boy\":\"true\",\"email\":\"xxx%40xxx.com\"}";
        Assert.assertEquals("UrlUtils.parseStringToMap", unDecodeExpected, JsonUtils.toJSONString(unDecodeActual));

        Map<String, String> decodeActual = UrlUtils.valueOf(paramsStr, true);
        String decodeExpected = "{\"phone\":\"1234****345\",\"id\":\"1\",\"boy\":\"true\",\"email\":\"xxx@xxx.com\"}";
        Assert.assertEquals("UrlUtils.parseStringToMap", decodeExpected, JsonUtils.toJSONString(decodeActual));
    }

    @Test
    public void notQuestionMarkConcat() {

        String url = "http://www.yabale.com/index.html";
        String params = "phone=1234****345&id=1&boy=true&email=xxx@xxx.com";

        String actual = UrlUtils.concat(url, params);

        String expected = "http://www.yabale.com/index.html?phone=1234****345&id=1&boy=true&email=xxx@xxx.com";

        Assert.assertEquals("UrlUtilsTest.notQuestionMarkConcat", expected, actual);
    }

    @Test
    public void questionMarkConcat() {

        String url = "http://www.yabale.com/index.html?language=cnj";
        String params = "phone=1234****345&id=1&boy=true&email=xxx@xxx.com";

        String actual = UrlUtils.concat(url, params);

        String expected = "http://www.yabale.com/index.html?language=cnj&phone=1234****345&id=1&boy=true&email=xxx@xxx.com";

        Assert.assertEquals("UrlUtilsTest.questionMarkConcat", expected, actual);
    }
}