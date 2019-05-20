package com.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditExpense extends AppCompatActivity {
    EditText name;
    EditText cost;
    EditText date;
    EditText notes;
    Spinner category;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_expense);

        name = findViewById(R.id.nameExpenseEdit);
        cost = findViewById(R.id.costExpenseEdit);
        date = findViewById(R.id.dateExpenseEdit);
        notes = findViewById(R.id.notesExpenseEdit);
        category = findViewById(R.id.categoryExpenseSpinner);
        okButton = findViewById(R.id.okExpenseButton);

        Expense expense = (Expense) getIntent().getSerializableExtra(Expense.class.getSimpleName());
        name.setText(expense.getName());
        cost.setText(String.valueOf(expense.getCost()));
        date.setText(expense.getDate());
        notes.setText(expense.getNotes());
        category.setSelection(getIndex(category, expense.getCategory()));

        cost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!(cost.getText().toString().equals(""))){
                    okButton.setEnabled(true);
                }
                else{
                    okButton.setEnabled(false);
                }
            }});

        View.OnClickListener listenerCreate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String costStr = cost.getText().toString();
                String dateStr = date.getText().toString();
                String notesStr = notes.getText().toString();
                String categoryStr = category.getSelectedItem().toString();

                Intent i = new Intent();
                i.putExtra("name", nameStr);
                i.putExtra("cost", costStr);
                i.putExtra("date", dateStr);
                i.putExtra("notes", notesStr);
                i.putExtra("category", categoryStr);
                setResult(3, i);
                finish();
            }
        };
        okButton.setOnClickListener(listenerCreate);
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