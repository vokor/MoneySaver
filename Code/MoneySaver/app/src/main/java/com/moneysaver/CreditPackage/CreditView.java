package com.moneysaver.CreditPackage;

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

import com.moneysaver.R;
import com.moneysaver.SQLite;

import java.util.ArrayList;

public class CreditView extends AppCompatActivity {
    private Button ok;
    private Button delete;

    private ArrayList<Credit> credits;
    private Credit credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_view);
        credit = (Credit) getIntent().getSerializableExtra(Credit.class.getSimpleName());
        final EditText name = findViewById(R.id.name);
        name.setText(credit.getName());
        final EditText cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(credit.getAllSum()));
        final EditText notes = findViewById(R.id.notes);
        notes.setText(credit.getNotes());
        String str = credit.getPayout().toString();
        final TextView savedValue = findViewById(R.id.savedValue);
        savedValue.setText(str);

        ok = findViewById(R.id.buttonOk);
        delete = findViewById(R.id.buttonDelete);

        credits = SQLite.getCreditList(this);
        name.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);

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
                    ok.setEnabled(true);
                } else {
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
                if ((!cost.getText().toString().equals("")) && checkName(name.getText().toString())) {
                    ok.setEnabled(true);
                    name.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                } else {
                    ok.setEnabled(false);
                    name.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
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
    }

    private boolean checkName(String name) {
        if (name.equals(""))
            return false;
        for (Credit credit1: credits) {
            if (name.equals(credit1.getName()) && !credit.getName().equals(name))
                return false;
        }
        return true;
    }

    private void dialogWindowSave(final String name, final String notes, final double cost) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreditView.this);
        builder.setTitle("Уведомление")
                .setMessage("Сохранить изменения?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(CreditView.this, "Отмена!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saveChanges(name, notes, cost);
                                Toast.makeText(CreditView.this, "Сохранено!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                Intent intent = new Intent(CreditView.this, ListCredit.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialogWindowDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreditView.this);
        builder.setTitle("Уведомление")
                .setMessage("Удалить кредит '" + credit.getName() + "'?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(CreditView.this, "Отмена!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLite.deleteCredit(CreditView.this, credit.getName());
                                Toast.makeText(CreditView.this, "Удалено!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                Intent intent = new Intent(CreditView.this, ListCredit.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveChanges(String name, String notes, double cost) {
        Credit newCredit = new Credit(name, cost, credit.getPayout(), notes);
        SQLite.updateCredit(CreditView.this, credit.getName(), newCredit);
    }
}