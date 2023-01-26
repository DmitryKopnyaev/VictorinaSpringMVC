package com.dimentor.victorina.filter;

import com.dimentor.victorina.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@Order(1)
public class AuthFilter implements Filter {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

 //        Cookie foundCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("hash")).findFirst().orElse(null);
        Cookie foundCookie = request.getCookies() == null ? null : Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("hash")).findFirst().orElse(null);
        String hash = foundCookie != null ? foundCookie.getValue() : null;
        System.out.println("filter hash: " + hash);

        String[] permittedMatchers = {"/auth/login", "/reg"}; //адреса допускаемые без аутентификации
        boolean uriHavePermittedMatchers = Arrays.stream(permittedMatchers).anyMatch(permMatcher -> request.getRequestURI().contains(request.getContextPath() + permMatcher));

        if (request.getRequestURI().endsWith("js") || request.getRequestURI().endsWith("css.map") || request.getRequestURI().endsWith(".css")
                || uriHavePermittedMatchers || hash != null && this.clientService.getByHash(hash) != null) {
            System.out.println(request.getRequestURI() + " permitted!");
            filterChain.doFilter(request, response);
        } else {
            System.out.println(request.getRequestURI() + " not permitted!");
            response.sendRedirect("/auth/login");
        }
    }
}
