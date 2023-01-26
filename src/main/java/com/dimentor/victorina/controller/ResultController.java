package com.dimentor.victorina.controller;

import com.dimentor.victorina.model.Result;
import com.dimentor.victorina.service.ClientService;
import com.dimentor.victorina.service.ResultService;
import com.dimentor.victorina.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/result")
@RestController
public class ResultController {

    private ClientService clientService;

    private TaskService taskService;

    private ResultService resultService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping("/answer")
    public Result addResult(@CookieValue(name = "id") long clientId,
                            @RequestParam long task, @RequestParam String answer) {
        Result result = new Result();
        result.setAnswer(answer);
        result.setClient(this.clientService.getClientById(clientId));
        result.setTask(this.taskService.getTaskById(task));
        try {
            this.resultService.saveResult(result);
        } catch (IllegalArgumentException ignored) {
        }
        return result;
    }
}
