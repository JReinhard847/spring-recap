package org.example.springrecap.model;

import java.util.List;

public record OpenAIRequest(String model, List<OpenAiMessage> messages, double temperature) {
}
