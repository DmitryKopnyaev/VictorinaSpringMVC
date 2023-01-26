package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Client;
import com.dimentor.victorina.model.Result;
import com.dimentor.victorina.model.Task;

import java.util.List;
public interface ResultService {
    Result getResultById(Long id);
    void saveResult(Result result);
    void updateResult(Result result);
    Result deleteResult(Long id);
    List<Result> getAll();
    Result getByClientAndTask(Client client, Task task);
}