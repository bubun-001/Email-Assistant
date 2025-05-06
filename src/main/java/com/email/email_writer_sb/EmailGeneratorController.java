package com.email.email_writer_sb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailGeneratorController {

            public ResponseEntity<String>generateEmail(@RequestBody EmailRequest emailRequest){
                return ResponseEntity.ok("");
            }
            //same handler method can be written without ResponseEntity but in that case we cant deal with status codes
//    public String generateEmailController(@RequestBody EmailRequest emailRequest){
//        return "Email generated successfully";
//    }

}
