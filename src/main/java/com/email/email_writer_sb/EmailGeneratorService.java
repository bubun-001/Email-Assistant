package com.email.email_writer_sb;

import org.springframework.stereotype.Service;

//This is the service class where the main logic is written . the API requests are dispatched by the controller to the service layer
@Service
public class EmailGeneratorService {

    public String generateEmailReply(EmailRequest emailRequest){
        //Build the prompt
        String prompt = buildPrompt(emailRequest);
        //Craft a request
        //Do req and get res
        //Return the res
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
