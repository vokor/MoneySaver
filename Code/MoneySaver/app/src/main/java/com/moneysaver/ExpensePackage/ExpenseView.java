package com.moneysaver.ExpensePackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.moneysaver.ExpensePackage.Expense;
import com.moneysaver.R;

public class ExpenseView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_view);
        Expense expense = (Expense) getIntent().getSerializableExtra(Expense.class.getSimpleName());
        TextView name = findViewById(R.id.textViewName);
        name.setText(expense.getName());
        TextView cost = findViewById(R.id.textViewCost);
        cost.setText(Double.toString(expense.getCost()));
        TextView category = findViewById(R.id.textViewCategory);
        category.setText(expense.getCategory());
        TextView date = findViewById(R.id.textViewDate);
        date.setText(expense.getDate());
        TextView notes = findViewById(R.id.textViewNotes);
        notes.setText(expense.getNotes());
    }
}