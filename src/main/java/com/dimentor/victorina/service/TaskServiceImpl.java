package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Task;
import com.dimentor.victorina.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTaskById(Long id) {
        return this.taskRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTask(Task task) {
        try {
            this.taskRepository.save(task);
        } catch (Exception e) {
            throw new IllegalArgumentException("This task already exists");
        }
    }

    @Override
    public void updateTask(Task task) {
        Task byId = this.getTaskById(task.getId());
        if (byId == null)
            throw new IllegalArgumentException("Task with this ID does not exist");
        this.taskRepository.save(task);
    }

    @Override
    public Task deleteTask(Long id) {
        Task taskById = this.getTaskById(id);
        if (taskById == null) return null;
        this.taskRepository.deleteById(id);
        return taskById;
    }

    @Override
    public List<Task> getAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksByTestId(long id) {
        return this.taskRepository.getTasksByTestId(id);
    }

    @Override
    public Task getTaskByQuestionAndCorrectAnswer(String question, String correctAnswer) {
        return this.taskRepository.getTaskByQuestionAndCorrectAnswer(question, correctAnswer).orElse(null);
    }
}