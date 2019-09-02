package com.moneysaver.Settings;

import java.util.ArrayList;

public class Container {

    private ArrayList<Category> categories;

    private SaveCategories saveCategories;

    private ArrayList<Category> commonCategories;

    public Container(ArrayList<Category> categories, SaveCategories saveCategories) {
        this.categories = categories;
        this.saveCategories = saveCategories;
        calculateCommonCategories();
    }

    private void calculateCommonCategories() {
        commonCategories = new ArrayList<>();
        for (Category category: categories) {
            commonCategories.add(new Category(category.getName(), category.getMaxSum(), category.getSpent(), category.getId()));
        }
        for (Category category: saveCategories.categories) {
            commonCategories.add(new Category(category.getName(), category.getMaxSum(), category.getSpent(), category.getId()));
        }
    }

    public ArrayList<Category> getCommonCategories() {
        return commonCategories;
    }

    public ArrayList<Category> getStartCategories() {
        return categories;
    }

    public int getSum() {
        int res = 0;
        for (Category category: commonCategories) {
            if (!category.deleted)
                res += category.getMaxSum();
        }
        return res;
    }

    public void saveEverything() {
        saveCategories.addCategories(commonCategories);
        saveCategories.deleteCategories();
    }

    public void abortEverithing() {
        saveCategories.deleteCategories();
    }

    private boolean checkUniqueName(int position, String newName) {
        if (commonCategories.get(position).getName().equals(newName))
            return true;
        for (Category category: commonCategories) {
            if (category.getName().equals(newName))
                return false;
        }
        return true;
    }

    public boolean updateCategory(int position, String newName, int newSum) {
        if (!checkUniqueName(position, newName))
            return false;
        if (!newName.equals(""))
            commonCategories.get(position).setName(newName);
        if (newSum == -1)
            return true;
        if (newSum > 0)
            commonCategories.get(position).setMaxSum(newSum);
        commonCategories.get(position).changed = true;
        return true;
    }

    public void abortChanges(int position) {
        int id = commonCategories.get(position).getId();
        for (Category category: categories) {
            if (category.getId() == id) {
                commonCategories.get(position).setMaxSum(category.getMaxSum());
                commonCategories.get(position).setName(category.getName());
                return;
            }
        }
        for (Category category: saveCategories.categories) {
            if (category.getId() == id) {
                commonCategories.get(position).setMaxSum(category.getMaxSum());
                commonCategories.get(position).setName(category.getName());
                return;
            }
        }
    }
}
