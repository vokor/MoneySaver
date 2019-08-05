package com.moneysaver.CreditPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.R;

public class EditCredit extends AppCompatActivity {
    EditText name;
    EditText cost;
    EditText notes;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_credit);

        name = findViewById(R.id.nameCreditEdit);
        cost = findViewById(R.id.costCreditEdit);
        notes = findViewById(R.id.notesCreditEdit);
        okButton = findViewById(R.id.okCreditButton);

        Credit credit = (Credit) getIntent().getSerializableExtra(Credit.class.getSimpleName());
        name.setText(credit.getName());
        cost.setText(String.valueOf(credit.getCost()));
        notes.setText(credit.getNotes());


        cost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!cost.getText().toString().equals("")) && (!name.getText().toString().equals(""))) {
                    okButton.setEnabled(true);
                } else {
                    okButton.setEnabled(false);
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
                if ((!cost.getText().toString().equals("")) && (!name.getText().toString().equals(""))) {
                    okButton.setEnabled(true);
                } else {
                    okButton.setEnabled(false);
                }
            }
        });

        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String costStr = cost.getText().toString();
                String notesStr = notes.getText().toString();

                Intent i = new Intent();
                i.putExtra("name", nameStr);
                i.putExtra("cost", costStr);
                i.putExtra("notes", notesStr);
                setResult(3, i);
                finish();
            }
        };
        okButton.setOnClickListener(listenerOk);
    }
}