package com.moneysaver.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.moneysaver.Config;
import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.StartScreen;

import java.util.ArrayList;

import static com.moneysaver.Config.dbName;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Category> categories;

    private ListView categoryList;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        db = getBaseContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        categories = getListCategory();
        ArrayList<String> baseCategories = new ArrayList<>();
        for (int j = 0; j < Config.baseCategories.length; j++) {
            boolean isContains = false;
            for (int i = 0; i < categories.size(); i++) {
                if (Config.baseCategories[j].equals(categories.get(i).getName()))
                    isContains = true;
            }
            if (!isContains)
                baseCategories.add(Config.baseCategories[j]);
        }

        MultiAutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, baseCategories);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        categoryList = findViewById(R.id.categoryList);
        SettingsAdapter categoriesAdapter = new SettingsAdapter(this, R.layout.list_categories, categories);
        categoryList.setAdapter(categoriesAdapter);

        final Button saveChanges = findViewById(R.id.saveChanges);
        final Button abortChanges = findViewById(R.id.abortChanges);

        saveChanges.setOnClickListener(this);
        abortChanges.setOnClickListener(this);
    }


    public ArrayList<Category> getListCategory() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addCategories:
                MultiAutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
                String newCategories = autoCompleteTextView.getText().toString();
                if (!newCategories.matches("([А-Яа-я_0-9], )*[А-Яа-я_0-9]"))
                {
                    String errorMessage = "Не удалось распознать введнные категории. Помните, что название может состоять только из букв, цифр и знака _";
                    errorInCategoriesField(errorMessage);
                    break;
                }
                String[] splitData = newCategories.replaceAll(" ", "").split(",");
                if (!checkUniqueNames(splitData))
                    break;
                Intent intent = new Intent(Settings.this, AddCategories.class);
                startActivity(intent);
                break;
            case R.id.saveChanges:
                dialogWindow();
                break;
            case R.id.abortChanges :
                break;
        }
    }

    boolean checkUniqueNames(String[] data) {
        for (int i = 0; i < data.length; i++)
            for (Category category: categories)
                if (data[i].equals(category.getName())) {
                    String errorMessage = "Название категории " + category.getName() + " уже занято. Придумайте другое.";
                    errorInCategoriesField(errorMessage);
                    return false;
                }
        return true;
    }

    private void errorInCategoriesField(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Ошибка")
                .setMessage(errorMessage)
                .setIcon(R.drawable.ic_error_black_24dp)
                .setCancelable(false)
                .setPositiveButton("Понятно",
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialogWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Уведомление")
                .setMessage("Сохранить изменения?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(Settings.this, "Нет!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(Settings.this, "Вы сделали правильный выбор",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}