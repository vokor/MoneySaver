package com.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class ListExpense extends AppCompatActivity {
    ListView vListView;
    ArrayList<Expense> list;
    Expense choosenExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expense);
        vListView = findViewById(R.id.ExpenselistView);
        showListView(list);

        Button button_add = findViewById(R.id.button_add);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListExpense.this, AddExpense.class);
                startActivityForResult(i, 2);
            }
        };
        button_add.setOnClickListener(listener);
    }

    private void showListView(ArrayList<Expense> list){
        final AdapterExpense a = new AdapterExpense(list);
        vListView.setAdapter(a);
        vListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Expense item = (Expense)a.getItem(position);
                choosenExpense = item;
                Intent intent = new Intent(view.getContext(), Expense.class);
                intent.putExtra(Expense.class.getSimpleName(), item);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    String b = data.getStringExtra("button");
                    switch (b) {
                        case "ok":
                            break;
                        case "edit": {
                            Intent intent = new Intent(ListExpense.this, EditExpense.class);
                            intent.putExtra(Expense.class.getSimpleName(), choosenExpense);
                            startActivityForResult(intent, 3);
                            break;
                        }
                        case "delete": {
                            //Intent intent = new Intent(SecondActivity.this, DeleteGoal.class);
                            //intent.putExtra(Goal.class.getSimpleName(), choosenGoal);
                            //startActivityForResult(intent, 4);
                            break;
                        }
                    }
                }
                case 2: {
                    // создать цель
                }
            }
        }
    }
}
