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

public class Settings extends AppCompatActivity {
    ArrayList<Category> categories;
    ListView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //dbRepository = new DbRepository(this);


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Category (Title TEXT,MaxSum INTEGER,Spent INTEGER);");

        db.execSQL("INSERT INTO Category VALUES('Еда', 0, 0);");
        db.execSQL("INSERT INTO Category VALUES('Транспорт', 0, 0);");
        db.execSQL("INSERT INTO Category VALUES('Здоровье', 0, 0);");
        db.execSQL("INSERT INTO Category VALUES('Развлечения', 0, 0);");
        db.execSQL("INSERT INTO Category VALUES('Платежи', 0, 0);");
        db.execSQL("INSERT INTO Category VALUES('Другое', 0, 0);");

        categories = getListCategory(db);

        ArrayList<String> baseCategories = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++)
            baseCategories.add(categories.get(i).getName());

        MultiAutoCompleteTextView autoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autocomplete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, baseCategories);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        categoryList = (ListView) findViewById(R.id.categoryList);
        SettingsAdapter adapter2 = new SettingsAdapter(this, R.layout.list_categories, categories);
        categoryList.setAdapter(adapter2);

        //EditText editText = (EditText)findViewById(R.id.newBalance);
        //editText.setHint(sqLite.getBalance());
    }


    public ArrayList<Category> getListCategory(SQLiteDatabase db) {
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Category;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {

                list.add(new Category(cursor.getString(0),cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}