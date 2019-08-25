package com.moneysaver.Settings;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.moneysaver.CreditPackage.AdapterCredit;
import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.R;

import java.util.ArrayList;

import static com.moneysaver.Config.dbName;
import static com.moneysaver.Config.getBalance;

public class AddCategories  extends AppCompatActivity implements View.OnClickListener{
    private SQLiteDatabase db;
    private AdapterCategory adapter;
    private String[] categoryNames;
    private int balance;
    private TextView balanceForCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_categories);

        db = getBaseContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        final Button saveChanges = findViewById(R.id.button_add);
        saveChanges.setOnClickListener(this);

        ListView vListView = findViewById(R.id.list_new_categories);
        Bundle arguments = getIntent().getExtras();
        categoryNames = arguments.getStringArray("array");
        balance = arguments.getInt("balance");
        showListView(vListView);
    }

    private ArrayList<Credit> getCreditList() {
        ArrayList<Credit> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Goal;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(new Credit(cursor.getString(1),cursor.getDouble(2), cursor.getDouble(3)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    private void showListView(ListView listView){
        balanceForCategories = findViewById(R.id.balanceForCategories);
        if (balance < 0)
            balanceForCategories.setText(Integer.toString(getBalance(db)));
        else
            balanceForCategories.setText(Integer.toString(balance));
        adapter = new AdapterCategory(categoryNames, balanceForCategories);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < categoryNames.length; i++) {
            Category category = (Category) adapter.getItem(i);

        }
    }
}
