package org.example.scsmin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class ProducerFallback implements FallbackProvider {

    //指定要处理的 service。
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
//        log.error(String.format("The service [%s] is unavailable,cause=-: ", route)+cause.getMessage());
        String causestr =  cause != null ? cause.getMessage() : "unknown";
        if(StringUtils.isNotBlank(causestr)){
            log.error(String.format("The service [%s] is unavailable,cause = ", route) + causestr);
        }else {
            log.error(String.format("The service [%s] is unavailable,cause = ", route) + cause.getCause());
        }
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return "SERVICE_UNAVAILABLE:" + route;
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(String.format("The service [%s] is unavailable.", route).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

}