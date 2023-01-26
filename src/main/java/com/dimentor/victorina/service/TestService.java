package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Test;

import java.util.List;
public interface TestService {
    Test getTestById(Long id);
    void saveTest(Test test);
    void updateTest(Test test);
    Test deleteTest(Long id);
    List<Test> getAll();
    Test getTestFromUrl(int amount, int category, String difficulty, String type);
}