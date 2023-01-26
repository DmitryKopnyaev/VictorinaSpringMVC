package com.dimentor.victorina.controller;

import com.dimentor.victorina.model.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/rest/file")
@RestController
public class FileRestController {

    //Get file from client and use by server
    @PostMapping(path = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveOnServer(@RequestPart MultipartFile document) {
        try {
            Test test = new ObjectMapper().readValue(document.getBytes(), Test.class);
            System.out.println("test.getTasks() = " + test.getTasks());
            return new ResponseEntity(test, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Save file on client
    @GetMapping(path = "/", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] saveOnClient(@ModelAttribute("test") Test test) {
        try {
            return new ObjectMapper().writeValueAsString(test).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
