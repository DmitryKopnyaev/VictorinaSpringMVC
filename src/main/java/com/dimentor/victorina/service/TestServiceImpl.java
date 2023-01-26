package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Category;
import com.dimentor.victorina.model.Task;
import com.dimentor.victorina.model.Test;
import com.dimentor.victorina.repository.CategoryRepository;
import com.dimentor.victorina.repository.TaskRepository;
import com.dimentor.victorina.repository.TestRepository;
import com.dimentor.victorina.util.ConnectServer;
import com.dimentor.victorina.util.Constants;
import com.dimentor.victorina.util.CustomTaskDeserializerFromNet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;
    private CategoryRepository categoryRepository;
    private TaskRepository taskRepository;

    @Autowired
    public void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Test getTestById(Long id) {
        return this.testRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTest(Test test) {
        try {
            Test newTest = new Test();
            this.testRepository.save(newTest);
            List<Task> tasks = test.getTasks();
            if (tasks.size() > 0) {
                Category category = tasks.get(0).getCategory();
                Category categoryByValue = this.categoryRepository.getByValue(category.getValue()).orElse(null);
                if (categoryByValue == null)
                    this.categoryRepository.save(category);
                else category = categoryByValue;
                for (Task t : tasks) {
                    t.setCategory(category);
                    Task taskByQuestAndCAnswer = this.taskRepository.getTaskByQuestionAndCorrectAnswer(t.getQuestion(), t.getCorrectAnswer()).orElse(null);
                    if (taskByQuestAndCAnswer == null)
                        try {
                            this.taskRepository.save(t);
                        } catch (Exception ignored) {
                            System.out.println("by task -> " + t.getQuestion() + " error = " +ignored.getMessage());
                        }
                    else t.setId(taskByQuestAndCAnswer.getId());
                }
                newTest.setTasks(tasks);
            }
            this.testRepository.save(test);
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }
    }

    @Override
    public void updateTest(Test test) {
        Test byId = this.getTestById(test.getId());
        if (byId == null)
            throw new IllegalArgumentException("Test with this ID does not exist");
        this.testRepository.save(test);
    }

    @Override
    public Test deleteTest(Long id) {
        Test testById = this.getTestById(id);
        if (testById == null) return null;
        this.testRepository.deleteById(id);
        return testById;
    }

    @Override
    public List<Test> getAll() {
        return this.testRepository.findAll();
    }

    @Override
    public Test getTestFromUrl(int amount, int category, String difficulty, String type) {

        String params = "?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=" + type + "&encode=base64";
        try (BufferedReader reader = ConnectServer.connect(Constants.SRC_URL + params, "GET", null)) {
            String collect = reader.lines().collect(Collectors.joining());

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Task.class, new CustomTaskDeserializerFromNet());
            mapper.registerModule(module);
            try {
                Test test = mapper.readValue(collect, Test.class);
                return test;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return new Test();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}