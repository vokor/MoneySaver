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

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.R;

public class GoalView extends AppCompatActivity {
    private Button ok;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_view);
        final Goal goal = (Goal) getIntent().getSerializableExtra(Goal.class.getSimpleName());
        final EditText name = findViewById(R.id.name);
        name.setText(goal.getName());
        final EditText cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(goal.getCost()));
        final EditText notes = findViewById(R.id.notes);
        notes.setText(goal.getNotes());
        TextView saved = findViewById(R.id.saved);
        String str = goal.getSaved().toString();
        final TextView savedValue = findViewById(R.id.savedValue);
        savedValue.setText(str);

        ok = findViewById(R.id.buttonOk);
        delete = findViewById(R.id.buttonDelete);

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
                if ((!cost.getText().toString().equals("")) && (!name.getText().toString().equals(""))) {
                    ok.setEnabled(true);
                } else {
                    ok.setEnabled(false);
                }
            }
        });

        View.OnClickListener listenerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("activity", "delete");
                setResult(1, i);
                finish();
            }
        };
        delete.setOnClickListener(listenerDelete);

        View.OnClickListener listenerOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newNotes = notes.getText().toString();
                String newCost = cost.getText().toString();

                if(goal.getName().equals(newName) && goal.getNotes().equals(newNotes) &&
                        goal.getCost().toString().equals(newCost)){
                    Intent i = new Intent();
                    i.putExtra("activity", "ok");
                    setResult(1, i);
                    finish();
                }
                else{
                    Intent i = new Intent();
                    i.putExtra("activity", "edit");
                    i.putExtra("name", newName);
                    i.putExtra("cost", newCost);
                    i.putExtra("notes", newNotes);
                    setResult(1, i);
                    finish();
                }
            }
        };
        ok.setOnClickListener(listenerOk);
    }
}