package com.microservices.user.service.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

@Slf4j
public class RestTemplateInterceptor implements org.springframework.http.client.ClientHttpRequestInterceptor {


    private OAuth2AuthorizedClientManager manager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {

        this.manager = manager;

    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client:").principal("internal").build())
                .getAccessToken().getTokenValue();

        log.info("Rest Template Interceptor : token : {}",token);

        request.getHeaders().add("Authorization", "bearer"+token);


        return execution.execute(request, body);
    }
}
