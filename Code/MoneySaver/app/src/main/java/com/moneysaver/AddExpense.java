package com.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddExpense extends AppCompatActivity {
    EditText name;
    EditText cost;
    EditText date;
    EditText notes;
    Spinner category;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        name = findViewById(R.id.nameExpenseEdit);
        cost = findViewById(R.id.costExpenseEdit);
        date = findViewById(R.id.dateExpenseEdit);
        notes = findViewById(R.id.notesExpenseEdit);
        category = findViewById(R.id.categoryExpenseSpinner);
        createButton = findViewById(R.id.createExpenseButton);

        createButton.setEnabled(false);

        cost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!(cost.getText().toString().equals(""))){
                    createButton.setEnabled(true);
                }
                else{
                    createButton.setEnabled(false);
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
                setResult(0, i);
                finish();
            }
        };
        createButton.setOnClickListener(listenerCreate);
    }
}
