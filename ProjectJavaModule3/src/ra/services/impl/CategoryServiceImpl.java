package ra.services.impl;

import ra.services.CRUD;
import ra.models.Category;
import ra.util.IOFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CRUD<Category> {
    private Map<String, Category> categories = new HashMap<>();

    public CategoryServiceImpl() {
        loadCategoriesFromFile();
    }

    @Override
    public void create(Category category) {
        categories.put(String.valueOf(category.getCategoryId()), category);
        saveCategoriesToFile();
    }

    @Override
    public Category read(String id) {
        Category category = categories.get(id);
        if (category == null) {
            System.out.println("Category with ID " + id + " not found.");
        }
        return category;
    }

    @Override
    public void update(Category category) {
        if (categories.containsKey(String.valueOf(category.getCategoryId()))) {
            categories.put(String.valueOf(category.getCategoryId()), category);
            saveCategoriesToFile();
        } else {
            System.out.println("Category with ID " + category.getCategoryId() + " not found for update.");
        }
    }

    @Override
    public void delete(String id) {
        if (categories.remove(id) != null) {
            saveCategoriesToFile();
        } else {
            System.out.println("Category with ID " + id + " not found for deletion.");
        }
    }

    @Override
    public List<Category> getAll() {
        return categories.values().stream().collect(Collectors.toList());
    }


    private void loadCategoriesFromFile() {
        IOFile<Category> categoryIOFile = new IOFile<>();
        try {
            List<Category> loadedCategories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
            for (Category category : loadedCategories) {
                categories.put(String.valueOf(category.getCategoryId()), category);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to read categories from file");
            e.printStackTrace();
        }
    }

    // Save all categories to a file
    private void saveCategoriesToFile() {
        IOFile<Category> categoryIOFile = new IOFile<>();
        try {
            categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, getAll());
            System.out.println("Categories saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Unable to write categories to file");
            e.printStackTrace();
        }
    }
}
