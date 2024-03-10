package com.example.weather.service.parser.utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Downloader {
    public static String downloadPage(String url) throws FileNotFoundException {
        final HttpGet httpGet = new HttpGet(url);
        String page = getResponse(httpGet, url);
        return page;
    }

    private static String getResponse(HttpGet httpGet, String url) throws FileNotFoundException {
        String page;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                final HttpEntity entity = response.getEntity();
                page = EntityUtils.toString(entity);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new FileNotFoundException(url + " was not downloaded");
        }
        return page;
    }
}
