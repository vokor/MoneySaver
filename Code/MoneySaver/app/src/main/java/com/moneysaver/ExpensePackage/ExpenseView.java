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

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.R;
import com.moneysaver.SQLite;
import com.moneysaver.Settings.Category;
import com.moneysaver.Settings.Settings;

import java.text.SimpleDateFormat;

public class ExpenseView extends AppCompatActivity {
    private Button ok;
    private Button delete;
    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_view);
        final Expense expense = (Expense) getIntent().getSerializableExtra(Expense.class.getSimpleName());
        final TextView name = findViewById(R.id.name);
        name.setText(expense.getName());
        final TextView cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(expense.getCost()));
        final TextView notes = findViewById(R.id.notes);
        notes.setText(expense.getNotes());
        final TextView date = findViewById(R.id.date);
        final Spinner category = findViewById(R.id.categoryExpenseSpinnerAdd);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SQLite.getCategoryNames(getBaseContext(), "Category"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setSelection(getIndex(category, expense.getCategory()));


        ok = findViewById(R.id.buttonOk);
        delete = findViewById(R.id.buttonDelete);

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

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}