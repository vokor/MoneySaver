package com.moneysaver.Settings;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

public class SaveCategories {

    SQLiteDatabase db;

    public ArrayList<Category> categories;

    public boolean isSaveCategories;

    public SaveCategories(SQLiteDatabase db) {
        this.db = db;
        categories = getListCategory();
        if (categories.size() > 0)
            isSaveCategories = true;
        else
            isSaveCategories = false;
    }

    public void saveCategories(ArrayList<Category> listToSave) {
        db.execSQL("DELETE FROM SaveCategories;");
        for (Category category: listToSave) {
            db.execSQL("INSERT INTO SaveCategories (Title, MaxSum, Spent) VALUES('"+ category.getName()
                    + "'," + category.getMaxSum() + ", 0);");
        }
    }

    public void addCategories() {
        for (Category category: categories) {
            db.execSQL("INSERT INTO Category (Title, MaxSum, Spent) VALUES('"+ category.getName()
                    + "'," + category.getMaxSum() + ", 0);");
        }
        db.execSQL("DELETE FROM SaveCategories;");
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

    private ArrayList<Category> getListCategory() {
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM SaveCategories;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(new Category(cursor.getString(1),cursor.getInt(2)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
