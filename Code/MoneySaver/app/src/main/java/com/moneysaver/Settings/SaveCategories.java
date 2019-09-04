package com.moneysaver.Settings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.MultiAutoCompleteTextView;

import com.moneysaver.SQLite;

import java.util.ArrayList;

public class SaveCategories {

    public ArrayList<Category> categories;

    public boolean isSaveCategories;

    private final String name = "SaveCategories";

    private Context context;

    public SaveCategories(Context context) {
        this.context = context;
        categories = SQLite.getCategoryList(context, name);
        if (categories.size() > 0)
            isSaveCategories = true;
        else
            isSaveCategories = false;
    }

    public void saveCategories(ArrayList<Category> listToSave) {
        SQLite.saveCategories(context, listToSave, name);
    }

    public void deleteCategories() {
        SQLite.deleteAllCategories(context, name);
    }

    public void addCategories(ArrayList<Category> listToSave) {
        SQLite.deleteAllCategories(context, "Category");
        SQLite.saveCategories(context, listToSave, "Category");
    }

    public void setNamesToEdit(MultiAutoCompleteTextView autoCompleteTextView) {
        String res = "";
        for (int i = 0; i < categories.size() - 1; i++)
            res += (categories.get(i).getName() + ", ");
        if (isSaveCategories) {
            res += categories.get(categories.size() - 1).getName();
            autoCompleteTextView.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        }
        autoCompleteTextView.setText(res);
    }
}
