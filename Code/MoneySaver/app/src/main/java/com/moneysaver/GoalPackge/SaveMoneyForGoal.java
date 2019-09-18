package com.moneysaver.GoalPackge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moneysaver.R;

public class SaveMoneyForGoal extends AppCompatActivity {
    private Button okButton;
    private EditText save;
    private double balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Goal goal = (Goal) getIntent().getSerializableExtra(Goal.class.getSimpleName());
        setContentView(R.layout.save_money);
        TextView name = findViewById(R.id.name);
        name.setText(goal.getName());
        TextView cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(goal.getCost()));
        TextView saved = findViewById(R.id.saved);
        saved.setText(String.valueOf(goal.getSaved()));
        save = findViewById(R.id.save);

        okButton = findViewById(R.id.buttonOk);

        save.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((Double.parseDouble(save.getText().toString()) >= 0) && (Double.parseDouble(save.getText().toString()) <= balance)) {
                    okButton.setEnabled(true);
                } else {
                    okButton.setEnabled(false);
                }
            }
        });

        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double moneyForSave = Double.parseDouble(save.getText().toString());

                Intent i = new Intent();
                i.putExtra("save", moneyForSave);
                setResult(5, i);
                finish();
            }
        };
        okButton.setOnClickListener(listenerOk);
    }
}