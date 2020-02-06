/*
 * HttpUtil.java
 * v1.0.0
 * 2018. 1. 1.
 * Copyright (c) 2018 RouteLab Co. All rights Reserved.
 */
package dev.study.alarm.utill;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Http 유틸
 *
 * @author ssm @hsociety.co.kr
 * @version 1.0.0
 */
@Slf4j
public class HttpUtil {

    public static CloseableHttpClient getHttpClient() {

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setConnectionRequestTimeout(5 * 1000)
                .setSocketTimeout(5 * 1000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();

    }
}
