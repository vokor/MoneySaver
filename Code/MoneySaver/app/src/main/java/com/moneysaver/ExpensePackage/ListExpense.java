package com.moneysaver.ExpensePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;

import java.util.ArrayList;

public class ListExpense extends AppCompatActivity {
    private ListView vListView;
    private ArrayList<Expense> list;
    private Expense choosenExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expense);
        vListView = findViewById(R.id.ExpenselistView);
        showListView();

        Button button_add = findViewById(R.id.button_add);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListExpense.this, AddExpense.class);
                startActivity(intent);
            }
        };
        button_add.setOnClickListener(listener);
    }

    private void showListView(){
        list = SQLite.getExpenseList(getBaseContext());
        final AdapterExpense a = new AdapterExpense(list);
        vListView.setAdapter(a);
        vListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Expense item = (Expense)a.getItem(position);
                choosenExpense = item;
                Intent intent = new Intent(view.getContext(), ExpenseView.class);
                intent.putExtra(Expense.class.getSimpleName(), item);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListExpense.this, MainActivity.class);
        startActivity(intent);
    }
}
