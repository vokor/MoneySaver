package com.moneysaver.CreditPackage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;
import com.moneysaver.StartScreen;

import java.util.ArrayList;

public class AddCredit extends AppCompatActivity {
    EditText name;
    EditText cost;
    EditText notes;
    Button createButton;
    ArrayList<Credit> credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit);

        name = findViewById(R.id.nameCreditEdit);
        cost = findViewById(R.id.costCreditEdit);
        notes = findViewById(R.id.notesCreditEdit);
        createButton = findViewById(R.id.createCreditButton);

        name.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        cost.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        credits = SQLite.getCreditList(this);

        StartButtonListener();
        StartCostListener();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCredit.this, ListCredit.class);
        startActivity(intent);
    }

    private void StartButtonListener() {
        createButton.setEnabled(false);

        View.OnClickListener listenerCreate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                double costStr = Double.parseDouble(cost.getText().toString());
                String notesStr = notes.getText().toString();
                SQLite.addCredit(AddCredit.this, new Credit(nameStr, costStr, notesStr));

                Intent intent = new Intent(AddCredit.this, ListCredit.class);
                startActivity(intent);
            }
        };
        createButton.setOnClickListener(listenerCreate);
    }

    private void StartCostListener() {
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
                if (checkName(name.getText().toString())) {
                    createButton.setEnabled(true);
                    name.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                } else {
                    createButton.setEnabled(false);
                    name.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    private boolean checkName(String name) {
        if (name.equals(""))
            return false;
        for (Credit credit: credits) {
            if (name.equals(credit.getName()))
                return false;
        }
        return true;
    }
}