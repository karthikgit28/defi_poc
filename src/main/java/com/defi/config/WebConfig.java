package com.defi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
	
	@Value("${straitx.base.url}")
	private String baseUrl;
	
	@Value("${straitx.api.key}")
	private String apiKey;
	
	  @Bean (name = "StraitX")
	  public WebClient webClientStraitx() {

	    WebClient webClient = WebClient.builder()
	      .baseUrl(baseUrl)
	      .defaultCookie("cookie-name", "cookie-value")
	      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	      .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
	      .defaultHeader("X-XFERS-APP-API-KEY", apiKey)
	      .build();
	    
	    return webClient;
	  }
	  
	  @Bean (name = "Hedera")
	  public WebClient webClientHedera() {

	    WebClient webClient = WebClient.builder()
//	      .baseUrl("https://37rou5ocah2evdfzzdy2kp7ykm0renvk.lambda-url.ap-southeast-1.on.aws")
	      .defaultCookie("cookie-name", "cookie-value")
	      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	      .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
	      .defaultHeader("Authorization", "abc123")
	      .build();
	    
	    return webClient;
	  }
}
