package com.email.email_writer_sb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This is the service class where the main logic is written . the API requests are dispatched by the controller to the service layer
@Service
public class EmailGeneratorService {


    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String getGeminiApiKey;

    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest){


        //Build the prompt
        String prompt = buildPrompt(emailRequest);
        //Craft a request
        Map<String, Object>requestbody = Map.of(
             "contents",new Object[]{
                     Map.of("parts",new Object[]{
                             Map.of("text",prompt)
                     })
                }
        );
        //when we use the Map.of() ->it becomes immutable and further nothing can be changed but in hashmap
        //it can be changed later , thats why hashmap is mostly used.
//        Map<String,Object>textMap = new HashMap<>();
//        textMap.put("text" , prompt);
//
//        Map<String , Object> partsMap= new HashMap<>();
//        partsMap.put("parts" , List.of(textMap));
//
//        List<Map<String , Object>>contentList = new ArrayList<>();
//        contentList.add(partsMap);
//
//        Map<String , Object> requestBody = new HashMap<>();
//        requestBody.put("contents" , contentList);
//        return requestBody;


        //Do req and get res
        String response = webClient.post()
                .uri(geminiApiUrl+getGeminiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestbody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Extract and return the response
        return extractResponseContent(response);

    }

    private String extractResponseContent(String response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
       // return "Generate an email for the content" + emailRequest.getEmailContent()+"with the tone"+emailRequest.getTone();
       StringBuilder prompt = new StringBuilder();
       prompt.append("Generate a professional reply for the following email content. Please dont generate any subject  ");
       if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()){
           prompt.append("Use a ").append(emailRequest.getTone()).append("tone .");
       }
       prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
       return prompt.toString();

    }
}
