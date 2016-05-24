package com.globo.globonetwork.client.http;


import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public static void loggingRequest(HttpRequest request) {
        if ( LOGGER.isDebugEnabled()) {
            HttpContent httpContent = request.getContent();
            String content = "";
            if (httpContent != null){
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    request.getContent().writeTo(output);
                    content = output.toString();

                } catch (IOException ex) {
                    LOGGER.warn("Error io during get content request " + ex.getMessage());
                    content = ex.getMessage();
                }
            }
            LOGGER.debug("[GloboNetworkAPI request] " + request.getRequestMethod() + " URL:" + request.getUrl() + " Content:" + content);
        }
    }
    public static void loggingResponse(Long startTime, HttpRequest request, Response helper) {
        if ( LOGGER.isDebugEnabled() ) {
            Long responseTime = new Date().getTime() - startTime;

            LOGGER.debug("[GloboNetworkAPI response] ResponseTime: " + responseTime + "ms " + request.getRequestMethod() + " URL:" + request.getUrl() + " StatusCode: " + helper.statusCode + " Content: " + helper.content);
        }
    }
}
