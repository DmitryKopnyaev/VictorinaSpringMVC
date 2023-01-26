package com.dimentor.victorina.controller;

import com.dimentor.victorina.model.Client;
import com.dimentor.victorina.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegistrationController {

    private ClientService clientService;

    @Autowired
    public void setUserService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getRegistrationPage(){
        return "registration";
    }

    @PostMapping()
    public String doRegistration(@RequestParam String login, @RequestParam String password){
        Client client = new Client(login, password);
        try {
            this.clientService.saveClient(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/auth/login";
    }
}
