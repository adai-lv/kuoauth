package com.kupug.kuoauth.utils;

import com.kupug.kuoauth.KuHttpClient;
import org.junit.Test;

public class HttpClientTest {

    @Test
    public void get() {
        String url = "https://gitee.com/api/v5/user";
        String responseBody = KuHttpClient.builder()
                .fromUrl(url)
                .queryParam("access_token", "7f1eedec497160fd202dbc5f676fb5b2")
                .timeout(2000)
                .get();

        System.out.println(responseBody);
    }

    @Test
    public void post() {
    }
}