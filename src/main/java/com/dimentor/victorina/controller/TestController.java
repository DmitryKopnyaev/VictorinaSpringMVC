package com.dimentor.victorina.controller;

import com.dimentor.victorina.model.*;
import com.dimentor.victorina.service.*;
import com.dimentor.victorina.util.Coding;
import com.dimentor.victorina.util.ConnectServer;
import com.dimentor.victorina.util.HttpMultipart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/test")
@Controller
public class TestController {

    private TaskService taskService;
    private TestService testService;
    private ClientService clientService;
    private ResultService resultService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    @Autowired
    public void setUserService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/index")
    public String first() {
        return "index";
    }

    @GetMapping("/collector")
    public String collector() {
        return "testCollector";
    }

    @GetMapping("/load")
    public String start(
            @RequestParam int amount,
            @RequestParam int category,
            @RequestParam String difficulty,
            @RequestParam String type,
            @RequestParam String variant,
            RedirectAttributes attributes) {
        Test loadedTest = this.testService.getTestFromUrl(amount, category, difficulty, type);
        if (variant.equalsIgnoreCase("start")) {
            this.testService.saveTest(loadedTest);

            attributes.addAttribute("test", loadedTest.getId());
            attributes.addAttribute("task", 0);
            return "redirect:/test/start";
        } else { // save on client
            loadedTest.getTasks().forEach(task -> task.setCorrectAnswer(Coding.code(task.getCorrectAnswer())));
            loadedTest.getTasks().forEach(task -> task.setIncorrectAnswers(task.getIncorrectAnswers().stream()
                    .map(Coding::code).collect(Collectors.toList())));

            attributes.addFlashAttribute("test", loadedTest);
            return "redirect:/rest/file/";
        }
    }

    //  /test/start?test=1&task=1
    @GetMapping("/start")
    public String runTest(@RequestParam long test,
                          @RequestParam long task,
                          Model model,
                          HttpServletResponse response) {
        //что лучше так (через базу) или через редиррект аттрибут
        Test testById = this.testService.getTestById(test);
        Task taskById = this.taskService.getTaskById(task);
        model.addAttribute("test", testById);
        model.addAttribute("task", taskById);

        Cookie cookieTestId = new Cookie("test", String.valueOf(testById.getId()));
        cookieTestId.setPath("/test");
        cookieTestId.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieTestId);

        return "testStart";
    }

    //  /test/answer?task=1&answer=some+text+answer
    @PostMapping("/answer")
    public String answer(@CookieValue(name = "test") long testId,               //testId нужен только для редиректа на тест
                         @CookieValue(name = "id") long clientId,
                         @RequestParam long task, @RequestParam String answer,
                         RedirectAttributes attributes) {
        Result result = new Result();
        result.setAnswer(answer);
        result.setClient(this.clientService.getClientById(clientId));
        result.setTask(this.taskService.getTaskById(task));
        try {
            this.resultService.saveResult(result);
        } catch (IllegalArgumentException ignored) {
        }

        attributes.addAttribute("test", testId);
        attributes.addAttribute("task", task);
        return "redirect:/test/start";
    }

    //  /test/finish?test=1
    @PostMapping("/finish")
    public String finish(@RequestParam long testId,
                         RedirectAttributes attributes) {
        Test testById = this.testService.getTestById(testId);
        testById.setFinished(true);
        this.testService.updateTest(testById);

        attributes.addAttribute("test", testId);
        attributes.addAttribute("task", 0);
        return "redirect:/test/start";
    }

    //  /test/file
    @PostMapping(path = "/file/load", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String loadFromClientFile(@RequestPart MultipartFile document, RedirectAttributes attributes) {
        try {
            Test test = new ObjectMapper().readValue(document.getBytes(), Test.class);
            test.getTasks().forEach(task -> task.setCorrectAnswer(Coding.code(task.getCorrectAnswer())));
            test.getTasks().forEach(task -> task.setIncorrectAnswers(task.getIncorrectAnswers().stream()
                    .map(Coding::code).collect(Collectors.toList())));
            this.testService.saveTest(test);

            attributes.addAttribute("test", test.getId());
            attributes.addAttribute("task", 0);
            return "redirect:/test/start";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
