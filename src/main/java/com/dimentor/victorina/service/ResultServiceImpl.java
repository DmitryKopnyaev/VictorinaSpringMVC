package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Client;
import com.dimentor.victorina.model.Result;
import com.dimentor.victorina.model.Task;
import com.dimentor.victorina.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private ResultRepository resultRepository;

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public Result getResultById(Long id) {
        return this.resultRepository.findById(id).orElse(null);
    }

    @Override
    public void saveResult(Result result) {
        try {
            this.resultRepository.save(result);
        } catch (Exception e) {
            throw new IllegalArgumentException("Result already exist");
        }
    }

    @Override
    public void updateResult(Result result) {
        Result byId = this.getResultById(result.getId());
        if (byId == null)
            throw new IllegalArgumentException("Result with this ID does not exist");
        this.resultRepository.save(result);
    }

    @Override
    public Result deleteResult(Long id) {
        Result resultById = this.getResultById(id);
        if (resultById == null) return null;
        this.resultRepository.deleteById(id);
        return resultById;
    }

    @Override
    public List<Result> getAll() {
        return this.resultRepository.findAll();
    }

    @Override
    public Result getByClientAndTask(Client client, Task task) {
        return this.resultRepository.getByClientAndTask(client, task).orElse(null);
    }

}