package com.moneysaver.GoalPackge;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.moneysaver.Config;
import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.CreditPackage.CreditView;
import com.moneysaver.CreditPackage.ListCredit;
import com.moneysaver.R;
import com.moneysaver.SQLite;
import com.moneysaver.SaveMoney;

public class GoalView extends AppCompatActivity {
    private Button ok;
    private Button delete;
    private Button save;
    private Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info_view);
        goal = (Goal) getIntent().getSerializableExtra(Goal.class.getSimpleName());
        final EditText name = findViewById(R.id.name);
        name.setText(goal.getName());
        final EditText cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(goal.getCost()));
        final EditText notes = findViewById(R.id.notes);
        notes.setText(goal.getNotes());
        String str = goal.getSaved().toString();
        final TextView savedValue = findViewById(R.id.savedValue);
        savedValue.setText(str);

        ok = findViewById(R.id.buttonOk);
        delete = findViewById(R.id.buttonDelete);
        save = findViewById(R.id.buttonSave);

        cost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (cost.getText().toString().equals("") ||
                            Double.parseDouble(cost.getText().toString()) - goal.getSaved() < 0) {
                        cost.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        ok.setEnabled(false);
                    } else {
                        cost.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        if (!name.getText().toString().equals("")) {
                            ok.setEnabled(true);
                        } else {
                            ok.setEnabled(false);
                        }
                    }
                } catch (Exception e) {
                    cost.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    ok.setEnabled(false);
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
                if (name.getText().toString().equals("")) {
                    name.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    ok.setEnabled(false);
                } else {
                    name.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    if (!cost.getText().toString().equals("")) {
                        ok.setEnabled(true);
                    } else {
                        ok.setEnabled(false);
                    }
                }
            }
        });

        View.OnClickListener listenerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowDelete();
            }
        };
        delete.setOnClickListener(listenerDelete);

        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newNotes = notes.getText().toString();
                double newCost = Double.parseDouble(cost.getText().toString());
                dialogWindowSave(newName, newNotes, newCost);

            }
        };
        ok.setOnClickListener(listenerOk);

        View.OnClickListener saveMoney = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalView.this, SaveMoney.class);
                intent.putExtra("Goal", goal);
                startActivity(intent);
            }
        };
        save.setOnClickListener(saveMoney);
    }


    private void dialogWindowSave(final String name, final String notes, final double cost) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalView.this);
        builder.setTitle("Уведомление")
                .setMessage("Сохранить изменения?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(GoalView.this, "Отмена!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saveChanges(name, notes, cost);
                                Toast.makeText(GoalView.this, "Сохранено!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                Intent intent = new Intent(GoalView.this, ListGoal.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialogWindowDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoalView.this);
        builder.setTitle("Уведомление")
                .setMessage("Удалить цель '" + goal.getName() + "'?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(GoalView.this, "Отмена!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLite.updateBalance(GoalView.this, goal.getSaved(),1);
                                SQLite.deleteGoal(GoalView.this, goal.getId());
                                Toast.makeText(GoalView.this, "Удалено!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                Intent intent = new Intent(GoalView.this, ListGoal.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveChanges(String name, String notes, double cost) {
        Goal newGoal = new Goal(name, cost, goal.getSaved(), notes);
        SQLite.updateGoal(GoalView.this, goal.getId(), newGoal);
    }
}