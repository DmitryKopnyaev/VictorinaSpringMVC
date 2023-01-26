package com.dimentor.victorina.util;

import com.dimentor.victorina.model.Task;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

//https://www.baeldung.com/jackson-object-mapper-tutorial
public class CustomTaskSerializerToFile extends StdSerializer<Task> {

    public CustomTaskSerializerToFile() {
        this(null);
    }

    protected CustomTaskSerializerToFile(Class<Task> t) {
        super(t);
    }

    @Override
    public void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        // начало записи объекта Task
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("category", task.getCategory().getValue());
        jsonGenerator.writeStringField("type", task.getDifficulty());
        jsonGenerator.writeStringField("difficulty", task.getDifficulty());
        jsonGenerator.writeStringField("question", task.getQuestion());
        jsonGenerator.writeStringField("correct_answer", Coding.code(task.getCorrectAnswer()));

        // начало записи массива
        jsonGenerator.writeArrayFieldStart("incorrect_answers");
        for (String s : task.getIncorrectAnswers())
            jsonGenerator.writeString(Coding.code(s));
        jsonGenerator.writeEndArray();
        // ^ конец записи массива

        // конец записи объекта Task
        jsonGenerator.writeEndObject();
    }

    /*public static void main(String[] args) {
        Test test = new Test();
        test.setResponseCode(0);
        Category category = new Category("History");

        Task task1 = new Task("multiple", "medium", "question1", "correct_answer1");
        task1.setIncorrectAnswers(List.of("1incorrect1", "1incorrect2", "1incorrect3", "1incorrect4"));
        task1.setCategory(category);
        Task task2 = new Task("multiple", "medium", "question2", "correct_answer2");
        task2.setIncorrectAnswers(List.of("2incorrect1", "2incorrect2", "2incorrect3", "2incorrect4"));
        test.setTasks(List.of(task1, task2));
        task2.setCategory(category);

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("CustomTaskSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(Task.class, new CustomTaskSerializer());
        objectMapper.registerModule(module);
        try {
            System.out.println(objectMapper.writeValueAsString(test));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/
}

