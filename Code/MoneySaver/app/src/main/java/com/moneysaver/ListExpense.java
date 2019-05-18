package com.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListExpense extends AppCompatActivity {
    ListView vListView;
    ArrayList<Expense> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expense);
        vListView = findViewById(R.id.ExpenselistView);
        showListView(list);
    }

    private void showListView(ArrayList<Expense> list){
        final Adapter a = new Adapter(list);
        vListView.setAdapter(a);
        vListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Object item = a.getItem(position);
                Intent intent = new Intent(view.getContext(), Expense.class);
                startActivity(intent);
            }
        });
    }
}
