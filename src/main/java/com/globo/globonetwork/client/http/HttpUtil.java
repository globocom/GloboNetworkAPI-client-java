package com.globo.globonetwork.client.http;


import com.globo.globonetwork.client.api.GloboNetworkAPI;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
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
            String context = getContextValue(request.getHeaders());

            LOGGER.debug("[GloboNetworkAPI request] method: " + request.getRequestMethod() +
                                                    " URL: " + request.getUrl() +
                                                    " X-Request-Context: " + context +
                                                    " Content: " + content);
        }
    }
    public static void loggingResponse(Long startTime, HttpRequest request, Response response) {
        if ( LOGGER.isDebugEnabled() ) {
            Long responseTime = new Date().getTime() - startTime;

            String context = response.getHeader(GloboNetworkAPI.CONTEXT_HEADER);
            String idContext = response.getHeader(GloboNetworkAPI.ID_HEADER);
            StringBuilder builder = new StringBuilder();
            builder.append("[GloboNetworkAPI response] ResponseTime: ")
                    .append(responseTime)
                    .append(" method: ").append(request.getRequestMethod())
                    .append(" StatusCode: ").append(response.statusCode)
                    .append(" X-Request-Context: ").append(context)
                    .append(" X-Request-Id: ").append(idContext)
                    .append(" Content: " ).append(response.content);

            LOGGER.debug(builder.toString());
        }
    }

    public static String getContextValue(HttpHeaders headers){
        return (String)headers.get(GloboNetworkAPI.CONTEXT_HEADER);
    }
}
