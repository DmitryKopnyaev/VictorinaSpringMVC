package com.dimentor.victorina.util;

import com.dimentor.victorina.model.Category;
import com.dimentor.victorina.model.Task;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;

public class CustomTaskDeserializerFromFile extends StdDeserializer<Task> {


    public CustomTaskDeserializerFromFile() {
        this(null);
    }

    public CustomTaskDeserializerFromFile(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Task task = new Task();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        String decodeCategory = node.get("category").asText();
        task.setCategory(new Category(decodeCategory));

        String decodeType = node.get("type").asText();
        task.setType(decodeType);

        String decodeDifficulty = node.get("difficulty").asText();
        task.setDifficulty(decodeDifficulty);

        String decodeQuestion = node.get("question").asText();
        task.setQuestion(decodeQuestion);

        String decodeCorrectAnswer = Coding.code(node.get("correct_answer").asText());
        task.setCorrectAnswer(decodeCorrectAnswer);

        Iterator<JsonNode> list = node.get("incorrect_answers").elements();

        while (list.hasNext()) {
            JsonNode n = list.next();
            String decodeIncorrectAnswer = Coding.code(n.asText());
            task.getIncorrectAnswers().add(decodeIncorrectAnswer);
        }
        return task;
    }

    /*public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("deserial");
        module.addDeserializer(Task.class, new CustomTaskDeserializerFromFile());
        mapper.registerModule(module);
        try {
            Test test = mapper.readValue(new File("out.json"), Test.class);
            List<Task> tasks = test.getTasks();
            System.out.println(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}
