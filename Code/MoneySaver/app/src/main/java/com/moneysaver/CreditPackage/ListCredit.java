package com.moneysaver.CreditPackage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.moneysaver.GoalPackge.Goal;
import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;

import java.util.ArrayList;

import static com.moneysaver.Config.dbName;

public class ListCredit extends AppCompatActivity {
    private ListView vListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_credit);
        vListView = findViewById(R.id.creditlist_view);
        showListView(SQLite.getCreditList(ListCredit.this));

        Button button_add = findViewById(R.id.button_add);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCredit.this, AddCredit.class);
                startActivity(intent);
            }
        };
        button_add.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListCredit.this, MainActivity.class);
        startActivity(intent);
    }

    private void showListView(ArrayList<Credit> list){
        final AdapterCredit a = new AdapterCredit(list);
        vListView.setAdapter(a);
        vListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Credit item = (Credit)a.getItem(position);
                Intent intent = new Intent(view.getContext(), CreditView.class);
                intent.putExtra(Credit.class.getSimpleName(), item);
                startActivity(intent);
            }
        });
    }
}
