package com.moneysaver.GoalPackge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moneysaver.R;

public class SaveMoneyForGoal extends AppCompatActivity {
    EditText money;
    Double balance = 0.0;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_money);
        money = findViewById(R.id.nameExpenseEdit);
        okButton = findViewById(R.id.buttonOk);

        money.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((Double.parseDouble(money.getText().toString()) >= 0) && (Double.parseDouble(money.getText().toString()) <= balance)) {
                    okButton.setEnabled(true);
                } else {
                    okButton.setEnabled(false);
                }
            }
        });

        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double moneyForSave = Double.parseDouble(money.getText().toString());

                Intent i = new Intent();
                i.putExtra("money", moneyForSave);
                setResult(5, i);
                finish();
            }
        };
        okButton.setOnClickListener(listenerOk);
    }
}