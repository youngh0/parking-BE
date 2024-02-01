package com.example.parking.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class CoordinateRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final String AUTH_HEADER = "Authorization";

    @Value("${kakao.key}")
    private String kakaoAuthHeader;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        request.getHeaders().set(AUTH_HEADER, kakaoAuthHeader);
        return execution.execute(request, body);
    }
}
