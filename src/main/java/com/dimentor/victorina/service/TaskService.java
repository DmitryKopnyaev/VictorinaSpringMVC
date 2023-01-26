package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Task;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);
    void saveTask(Task task);
    void updateTask(Task task);
    Task deleteTask(Long id);
    List<Task> getAll();
    List<Task> getTasksByTestId(long id);
    Task getTaskByQuestionAndCorrectAnswer(String question, String correctAnswer);
}