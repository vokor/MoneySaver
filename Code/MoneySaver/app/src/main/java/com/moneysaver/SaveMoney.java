package com.moneysaver;

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
import android.widget.TextView;

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.CreditPackage.CreditView;
import com.moneysaver.CreditPackage.ListCredit;
import com.moneysaver.GoalPackge.Goal;
import com.moneysaver.GoalPackge.ListGoal;
import com.moneysaver.R;

public class SaveMoney extends AppCompatActivity {
    private Button okButton;
    private EditText save;
    private double balance;
    private String className;
    private Goal goal;
    private Credit credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_money);

        balance = SQLite.getBalance(SaveMoney.this);
        goal = (Goal) getIntent().getSerializableExtra("Goal");
        if (goal == null) {
            className = "Credit";
            credit = (Credit) getIntent().getSerializableExtra("Credit");
            setView("Кредит: " + credit.getName(), Double.toString(credit.getAllSum()), Double.toString(credit.getPayout()));
        } else {
            className = "Goal";
            setView("Цель: " + goal.getName(), Double.toString(goal.getCost()), Double.toString(goal.getSaved()));
        }
        startSaveListener();
        startButtonListener();
    }

    private boolean checkValue(double moneyForSave) {
        double sum = className.equals("Credit") ? credit.getAllSum() : goal.getCost();
        double payout = className.equals("Credit") ? credit.getPayout() : goal.getSaved();

        return balance - moneyForSave >= Config.EPS &&
                sum - payout - moneyForSave >= Config.EPS &&
                balance >= Config.EPS;
    }

    private void startButtonListener() {
        okButton = findViewById(R.id.buttonOk);
        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double moneyForSave = Double.parseDouble(save.getText().toString());
                SQLite.updateBalance(SaveMoney.this,moneyForSave, 2);
                Intent intent;
                if (className.equals("Credit")) {
                    credit.setPayout(credit.getPayout() + moneyForSave);
                    SQLite.updateCredit(SaveMoney.this, credit.getName(), credit);
                    intent = new Intent(SaveMoney.this, ListCredit.class);
                } else {
                    goal.setSaved(goal.getSaved() + moneyForSave);
                    SQLite.updateGoal(SaveMoney.this, goal.getId(), goal);
                    intent = new Intent(SaveMoney.this, ListGoal.class);
                }
                startActivity(intent);
            }
        };
        okButton.setOnClickListener(listenerOk);
    }

    private void startSaveListener() {
        save = findViewById(R.id.save);
        save.setText("0");
        save.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                double moneyForSave;
                try {
                    moneyForSave = Double.parseDouble(save.getText().toString());
                    if (checkValue(moneyForSave)) {
                        okButton.setEnabled(true);
                        save.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    } else {
                        okButton.setEnabled(false);
                        save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    }
                } catch (Exception e) {
                    okButton.setEnabled(false);
                    save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    private void setView(String name, String cost, String saved) {
        TextView nameView = findViewById(R.id.name);
        nameView.setText(name);
        TextView costView = findViewById(R.id.cost);
        costView.setText(cost);
        TextView savedView = findViewById(R.id.saved);
        savedView.setText(saved);

    }
}