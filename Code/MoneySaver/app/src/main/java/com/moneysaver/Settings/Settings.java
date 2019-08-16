package com.moneysaver.Settings;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import com.moneysaver.R;

import java.util.ArrayList;

import static com.moneysaver.StartScreen.dbName;

public class Settings extends AppCompatActivity {

    private ArrayList<Category> categories;

    private ListView categoryList;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        db = getBaseContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        categories = getListCategory(db);

        ArrayList<String> baseCategories = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++)
            baseCategories.add(categories.get(i).getName());

        MultiAutoCompleteTextView autoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autocomplete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, baseCategories);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        categoryList = (ListView) findViewById(R.id.categoryList);
        SettingsAdapter categoriesAdapter = new SettingsAdapter(this, R.layout.list_categories, categories);
        categoryList.setAdapter(categoriesAdapter);
    }


    public ArrayList<Category> getListCategory(SQLiteDatabase db) {
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Category;", null);
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