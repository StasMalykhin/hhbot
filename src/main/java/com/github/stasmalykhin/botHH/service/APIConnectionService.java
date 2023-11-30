package com.github.stasmalykhin.botHH.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * @author Stanislav Malykhin
 */
@Service
@ConfigurationProperties(prefix = "headhunter")
@Getter
@Setter
public class APIConnectionService {
    private String userAgent;
    private String accessToken;

    public HttpEntity<HttpHeaders> createRequestWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", userAgent);
        headers.add("Bearer", accessToken);
        return new HttpEntity<>(headers);
    }
}
