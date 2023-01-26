package com.dimentor.victorina.controller;

import com.dimentor.victorina.model.Client;
import com.dimentor.victorina.service.ClientService;
import com.dimentor.victorina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private ClientService clientService;

    @Autowired
    public void setUserService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        Client client = this.clientService.getByLoginAndPassword(login, password);
        if (client == null) {
            System.out.println("User not found");
            return "redirect:/auth/login";
        }
        String hash = StringUtil.generateHash();
        Cookie cookieId = new Cookie("id", String.valueOf(client.getId()));
        Cookie cookieHash = new Cookie("hash", hash);
        cookieId.setPath("/");
        cookieHash.setPath("/");
        cookieId.setMaxAge(30 * 60);
        cookieHash.setMaxAge(30 * 60);
        response.addCookie(cookieId);
        response.addCookie(cookieHash);
        client.setHash(hash);
        this.clientService.saveClient(client);
        System.out.println("Cookie " + hash + " saved");
        return "redirect:/test/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        response.addCookie(new Cookie("id", ""));
        response.addCookie(new Cookie("hash", ""));
        return "redirect:/auth/login";
    }
}
