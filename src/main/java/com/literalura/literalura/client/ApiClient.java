package com.literalura.literalura.client;

import com.literalura.literalura.dto.ApiResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiClient {

    private final WebClient webClient;

    public ApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://gutendex.com").build();
    }

    public ApiResponseDTO searchBooks(String query) {
        return this.webClient.get()
                .uri("/books/?search={query}", query)
                .retrieve()
                .bodyToMono(ApiResponseDTO.class)
                .block();
    }
}
