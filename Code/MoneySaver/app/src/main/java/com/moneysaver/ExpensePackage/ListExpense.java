package com.moneysaver.ExpensePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.moneysaver.GoalPackge.DeleteGoal;
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
                Intent i = new Intent(ListExpense.this, AddExpense.class);
                startActivityForResult(i, 2);
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
                            Intent intent = new Intent(ListExpense.this, DeleteGoal.class);
                            startActivityForResult(intent, 4);
                            break;
                        }
                    }
                }
                case 2: {
                    Bundle arguments = data.getExtras();
                    Expense expense = (Expense)arguments.getSerializable("value");
                    SQLite.AddExpense(getBaseContext(), expense);
                    SQLite.updateCategory(getBaseContext(), expense);
                    break;
                }
                case 3: {
                    // edit goal
                    break;
                }
                case 4: {
                    String b = data.getStringExtra("button");
                    if(b.equals("delete")){
                        // delete goal
                    }
                    break;
                }
            }
        }
    }
}
