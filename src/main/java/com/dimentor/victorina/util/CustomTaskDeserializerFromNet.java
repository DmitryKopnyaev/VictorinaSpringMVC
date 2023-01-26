package com.dimentor.victorina.util;

import com.dimentor.victorina.model.Category;
import com.dimentor.victorina.model.Task;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

//https://www.baeldung.com/jackson-object-mapper-tutorial
public class CustomTaskDeserializerFromNet extends StdDeserializer<Task> {

    public CustomTaskDeserializerFromNet() {
        this(null);
    }

    public CustomTaskDeserializerFromNet(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Task task = new Task();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        String encodeCategory = node.get("category").asText();
        String decodeCategory = new String(Base64.getDecoder().decode(encodeCategory));
        Category category = new Category();
        category.setValue(decodeCategory);
        task.setCategory(category);

        String encodeType = node.get("type").asText();
        String decodeType = new String(Base64.getDecoder().decode(encodeType));
        task.setType(decodeType);

        String encodeDifficulty = node.get("difficulty").asText();
        String decodeDifficulty = new String(Base64.getDecoder().decode(encodeDifficulty));
        task.setDifficulty(decodeDifficulty);

        String encodeQuestion = node.get("question").asText();
        String decodeQuestion = new String(Base64.getDecoder().decode(encodeQuestion));
        task.setQuestion(decodeQuestion);

        String encodeCorrectAnswer = node.get("correct_answer").asText();
        String decodeCorrectAnswer = new String(Base64.getDecoder().decode(encodeCorrectAnswer));
        task.setCorrectAnswer(decodeCorrectAnswer);

        Iterator<JsonNode> list = node.get("incorrect_answers").elements();

        while (list.hasNext()) {
            JsonNode n = list.next();
            String encodeIncorrectAnswer = n.asText();
            String decodeIncorrectAnswer = new String(Base64.getDecoder().decode(encodeIncorrectAnswer));
            task.getIncorrectAnswers().add(decodeIncorrectAnswer);
        }
        return task;
    }

    /*public static void main(String[] args) {
        *//*String o = "{\n" +
                "      \"category\": \"U2NpZW5jZSAmIE5hdHVyZQ==\",\n" +
                "      \"type\": \"bXVsdGlwbGU=\",\n" +
                "      \"difficulty\": \"bWVkaXVt\",\n" +
                "      \"question\": \"V2hpY2ggcHN5Y2hvbG9naWNhbCB0ZXJtIHJlZmVycyB0byB0aGUgc3RyZXNzIG9mIGhvbGRpbmcgY29udHJhc3RpbmcgYmVsaWVmcz8=\",\n" +
                "      \"correct_answer\": \"Q29nbml0aXZlIERpc3NvbmFuY2U=\",\n" +
                "      \"incorrect_answers\": [\n" +
                "        \"RmxpcC1GbG9wIFN5bmRyb21l\",\n" +
                "        \"U3BsaXQtQnJhaW4=\",\n" +
                "        \"QmxpbmQgU2lnaHQ=\"\n" +
                "      ]\n" +
                "    }";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("deserial");
        module.addDeserializer(Task.class, new CustomTaskDeserializer());
        mapper.registerModule(module);
        try {
            Task task = mapper.readValue(o, Task.class);
            System.out.println(task);
        } catch (IOException e) {
            e.printStackTrace();
        }*//*

        *//*String b = "{\n" +
                "  \"response_code\": 0,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"category\": \"U2NpZW5jZSAmIE5hdHVyZQ==\",\n" +
                "      \"type\": \"bXVsdGlwbGU=\",\n" +
                "      \"difficulty\": \"bWVkaXVt\",\n" +
                "      \"question\": \"V2hpY2ggcHN5Y2hvbG9naWNhbCB0ZXJtIHJlZmVycyB0byB0aGUgc3RyZXNzIG9mIGhvbGRpbmcgY29udHJhc3RpbmcgYmVsaWVmcz8=\",\n" +
                "      \"correct_answer\": \"Q29nbml0aXZlIERpc3NvbmFuY2U=\",\n" +
                "      \"incorrect_answers\": [\n" +
                "        \"RmxpcC1GbG9wIFN5bmRyb21l\",\n" +
                "        \"U3BsaXQtQnJhaW4=\",\n" +
                "        \"QmxpbmQgU2lnaHQ=\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"category\": \"R2VvZ3JhcGh5\",\n" +
                "      \"type\": \"bXVsdGlwbGU=\",\n" +
                "      \"difficulty\": \"ZWFzeQ==\",\n" +
                "      \"question\": \"V2hhdCBpcyB0aGUgb2ZmaWNpYWwgbGFuZ3VhZ2Ugb2YgQ29zdGEgUmljYT8=\",\n" +
                "      \"correct_answer\": \"U3BhbmlzaA==\",\n" +
                "      \"incorrect_answers\": [\n" +
                "        \"RW5nbGlzaA==\",\n" +
                "        \"UG9ydHVndWVzZQ==\",\n" +
                "        \"Q3Jlb2xl\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Task.class, new CustomTaskDeserializer());
        mapper.registerModule(module);
        try {
            Test test = mapper.readValue(b, Test.class);
            System.out.println(test.getTasks());
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
    }*/
}
