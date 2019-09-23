package com.moneysaver.GoalPackge;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moneysaver.ExpensePackage.AddExpense;
import com.moneysaver.ExpensePackage.ListExpense;
import com.moneysaver.R;
import com.moneysaver.SQLite;

public class AddGoal extends AppCompatActivity {
    EditText name;
    EditText cost;
    EditText notes;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);

        name = findViewById(R.id.nameGoalEdit);
        cost = findViewById(R.id.costGoalEdit);
        notes = findViewById(R.id.notesGoalEdit);
        createButton = findViewById(R.id.createGoalButton);

        name.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        cost.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        createButton.setEnabled(false);

        cost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!cost.getText().toString().equals(""))) {
                    cost.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    if (!name.getText().toString().equals("")) {
                        createButton.setEnabled(true);
                    } else {
                        createButton.setEnabled(false);
                    }
                } else {
                    cost.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!name.getText().toString().equals("")) {
                    createButton.setEnabled(true);
                    name.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                } else {
                    createButton.setEnabled(false);
                    name.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

        View.OnClickListener listenerCreate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                double newCost = Double.parseDouble(cost.getText().toString());
                String notesStr = notes.getText().toString();
                SQLite.addGoal(AddGoal.this, new Goal(nameStr, newCost, 0, notesStr));
                Intent intent = new Intent(AddGoal.this, ListGoal.class);
                startActivity(intent);
            }
        };
        createButton.setOnClickListener(listenerCreate);
    }
}
