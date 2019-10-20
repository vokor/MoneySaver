package com.moneysaver.ExpensePackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;

public class DeleteExpense extends AppCompatActivity {
    private Button deleteButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_expense);

        final Expense expense = (Expense) getIntent().getSerializableExtra(Expense.class.getSimpleName());

        deleteButton = findViewById(R.id.deleteButton);
        cancelButton = findViewById(R.id.cancelButton);

        View.OnClickListener listenerCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteExpense.this, MainActivity.class);
                startActivity(intent);
            }
        };
        cancelButton.setOnClickListener(listenerCancel);

        View.OnClickListener listenerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteExpense(DeleteExpense.this, expense);
                Intent intent = new Intent(DeleteExpense.this, MainActivity.class);
                startActivity(intent);
            }
        };
        deleteButton.setOnClickListener(listenerDelete);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeleteExpense.this, MainActivity.class);
        startActivity(intent);
    }

    public static void deleteExpense(Context context, Expense expense) {
        SQLite.deleteExpense(context, expense.getId());
        expense.setCost(-expense.getCost());
        SQLite.updateCategory(context, expense);
    }
}
