package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Category;
import com.dimentor.victorina.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        try {
            this.categoryRepository.save(category);
        } catch (Exception e) {
            throw new IllegalArgumentException("This category entity already exists");
        }
    }

    @Override
    public void updateCategory(Category category) {
        Category byId = this.getCategoryById(category.getId());
        if (byId == null)
            throw new IllegalArgumentException("Category with this ID does not exist");
        this.categoryRepository.save(category);
    }

    @Override
    public Category deleteCategory(Long id) {
        Category categoryById = this.getCategoryById(id);
        if (categoryById == null) return null;
        this.categoryRepository.deleteById(id);
        return categoryById;
    }

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getByValue(String value) {
        return this.categoryRepository.getByValue(value).orElse(null);
    }

//    @Override
//    public List<Category> getFromUrl() {
//        try (BufferedReader reader = ConnectServer.connect("https://opentdb.com/api_category.php", "GET", null)){
//            String collect = reader.lines().collect(Collectors.joining("\n"));
//            JSONObject objects = new JSONObject(collect);
//            JSONObject trivia_categories = objects.getJSONObject("trivia_categories");
//            System.out.println(trivia_categories);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }

    /*@Override
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
    }*/
}