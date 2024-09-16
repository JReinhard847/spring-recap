package org.example.springrecap;


import org.example.springrecap.model.OpenAIRequest;
import org.example.springrecap.model.OpenAiMessage;
import org.example.springrecap.model.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OpenAIService {
    private final RestClient client;

    public OpenAIService(@Value("${BASE_URL}") String baseUrl,
                         @Value("${AUTH_KEY}") String key,RestClient.Builder builder
    ) {
        client = builder
                .defaultHeader("Authorization", "Bearer " + key)
                .baseUrl(baseUrl)
                .build();
    }

    public String spellCheck(String s){
        String query = "Check the spelling and grammar of: "+s+". Provide the corrected phrase or repeat it if it was already correct. dont use any prefixes to the answer";
        OpenAIRequest request = new OpenAIRequest("gpt-4o-mini",
                List.of(new OpenAiMessage("user", query)),
                0.2
        );


        try {
            OpenAiResponse response = client.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(OpenAiResponse.class);
            return response.getAnswer();
        } catch (Exception e) {
            return s;
        }

    }
}
