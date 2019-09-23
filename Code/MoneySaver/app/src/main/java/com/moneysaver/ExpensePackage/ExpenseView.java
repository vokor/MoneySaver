package com.moneysaver.ExpensePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

import com.moneysaver.ExpensePackage.Expense;
import com.moneysaver.R;

public class ExpenseView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_view);
        Expense expense = (Expense) getIntent().getSerializableExtra(Expense.class.getSimpleName());
        TextView name = findViewById(R.id.name);
        name.setText(expense.getName());
        TextView cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(expense.getCost()));
        TextView notes = findViewById(R.id.notes);
        notes.setText(expense.getNotes());
        TextView date = findViewById(R.id.date);
        date.setText(expense.getDate());
        TextView category = findViewById(R.id.category);
        category.setText(expense.getCategory());
        TextView notesView = findViewById(R.id.notesTextView);
        if(notes.getText().toString().isEmpty()){
            notesView.setVisibility(View.INVISIBLE);
        }

        Button ok = findViewById(R.id.buttonOk);
        Button delete = findViewById(R.id.buttonDelete);

        View.OnClickListener listenerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("activity", "delete");
                setResult(1, i);
                finish();
            }
        };
        delete.setOnClickListener(listenerDelete);

        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("activity", "ok");
                setResult(1, i);
                finish();
            }
        };
        ok.setOnClickListener(listenerOk);
    }
}