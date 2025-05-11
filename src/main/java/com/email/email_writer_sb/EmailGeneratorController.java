package com.email.email_writer_sb;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailGeneratorController {
//our end-point - /api/email/generate
    private final EmailGeneratorService emailGeneratorService;

    @PostMapping("/generate")
            public ResponseEntity<String>generateEmail(@RequestBody EmailRequest emailRequest){
                String response = emailGeneratorService.generateEmailReply(emailRequest);
                return ResponseEntity.ok(response);
            }
            //same handler method can be written without ResponseEntity but in that case we cant deal with status codes
//    public String generateEmailController(@RequestBody EmailRequest emailRequest){
//        return "Email generated successfully";
//    }

}
