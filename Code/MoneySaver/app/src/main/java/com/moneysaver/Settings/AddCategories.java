package com.moneysaver.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moneysaver.R;

import java.util.ArrayList;

public class AddCategories  extends AppCompatActivity implements View.OnClickListener{
    private AdapterCategory adapter;
    private String[] categoryNames;
    private int balance;
    private TextView balanceForCategories;
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_categories);

        final Button saveChanges = findViewById(R.id.button_add);
        saveChanges.setOnClickListener(this);

        ListView vListView = findViewById(R.id.list_new_categories);
        Bundle arguments = getIntent().getExtras();

        categoryNames = arguments.getStringArray("array");
        categories = new ArrayList<>();
        for (String category: categoryNames)
            categories.add(new Category(category, 0, 0));
        balance = arguments.getInt("balance");

        showListView(vListView);
    }

    private void showListView(ListView listView){
        balanceForCategories = findViewById(R.id.balanceForCategories);
        balanceForCategories.setText(Integer.toString(balance));
        adapter = new AdapterCategory(categories, balanceForCategories);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < categories.size(); i++) {
            if (!categories.get(i).approved) {
                String errorMessage = "Для категории '" + categories.get(i).getName() + "' установлена недопустимая максимальная сумма!";
                Settings.errorShow(errorMessage, AddCategories.this);
                return;
            }
        }
        dialogWindow();
    }

    private void dialogWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddCategories.this);
        builder.setTitle("Уведомление")
                .setMessage("Сохранить изменения?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(AddCategories.this, "Нет!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(AddCategories.this, "Сохранено!",
                                        Toast.LENGTH_LONG).show();
                                SaveCategories saved = new SaveCategories(getBaseContext());
                                saved.saveCategories(categories);
                                Intent intent = new Intent(AddCategories.this, Settings.class);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
