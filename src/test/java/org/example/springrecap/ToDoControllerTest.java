package org.example.springrecap;

import org.example.springrecap.model.OpenAIRequest;
import org.example.springrecap.model.OpenAiMessage;
import org.example.springrecap.model.ToDOObject;
import org.example.springrecap.model.ToDOStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer mockWebServer;



    @Test
    void addToDo() throws Exception {
        String incorrectSpelling = "clean appartment";
        String query = "Check the spelling and grammar of: " + incorrectSpelling + ". Provide the corrected phrase or repeat it if it was already correct. dont use any prefixes to the answer";

        mockWebServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
{"model":"gpt-4o-mini",
"messages":[{"role":"user","content":"Check the spelling and grammar of: clean appartment. Provide the corrected phrase or repeat it if it was already correct. dont use any prefixes to the answer"}],"temperature":0.2}
"""))
                .andRespond(withSuccess("""
                                {
                                "choices":[{"message":
                                {
                                "content":"clean apartment"
                                }
                                }]
                                }
                                """,
                        MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                      "description": "clean appartment",
                                      "status" : "OPEN"
                                 }"""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                {
                      "description": "clean apartment",
                      "status" : "OPEN"
                }
                """));
        mockWebServer.verify();
    }
}