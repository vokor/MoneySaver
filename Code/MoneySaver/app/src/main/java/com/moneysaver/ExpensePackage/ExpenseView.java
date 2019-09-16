package com.moneysaver.ExpensePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.R;
import com.moneysaver.SQLite;
import com.moneysaver.Settings.Category;
import com.moneysaver.Settings.Settings;

import java.text.SimpleDateFormat;

public class ExpenseView extends AppCompatActivity {
    private Button ok;
    private Button delete;
    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_view);
        final Expense expense = (Expense) getIntent().getSerializableExtra(Expense.class.getSimpleName());
        final EditText name = findViewById(R.id.name);
        name.setText(expense.getName());
        final EditText cost = findViewById(R.id.cost);
        cost.setText(String.valueOf(expense.getCost()));
        final EditText notes = findViewById(R.id.notes);
        notes.setText(expense.getNotes());
        final EditText date = findViewById(R.id.date);
        final Spinner category = findViewById(R.id.categoryExpenseSpinnerAdd);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SQLite.getCategoryNames(getBaseContext(), "Category"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setSelection(getIndex(category, expense.getCategory()));


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
                String newDate = date.getText().toString();
                String newCategory = category.getSelectedItem().toString();

                if(expense.getName().equals(newName) && expense.getNotes().equals(newNotes) &&
                        expense.getCost().toString().equals(newCost) &&
                expense.getDate().equals(newDate) && expense.getCategory().equals(newCategory)){
                    Intent i = new Intent();
                    i.putExtra("activity", "ok");
                    setResult(1, i);
                    finish();
                }
                else{
                    if (!checkInfo(newDate, newCategory, Double.valueOf(newCost))) {
                        return;
                    }

                    Intent i = new Intent();
                    i.putExtra("activity", "edit");
                    i.putExtra("name", newName);
                    i.putExtra("cost", newCost);
                    i.putExtra("date", newDate);
                    i.putExtra("category", newCategory);
                    i.putExtra("notes", newNotes);
                    setResult(1, i);
                    finish();
                }
            }
        };
        ok.setOnClickListener(listenerOk);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private boolean checkInfo(String date, String name, double cost) {
        try {
            format.parse(date);
        } catch (Exception e) {
            String err = "Недопустимый формат даты. Дата оформляется по шаблону: 'dd.MM.yyyy'";
            Settings.errorShow(err, getBaseContext());
            return false;
        }
        for (Category category: SQLite.getCategoryList(getBaseContext(), "Category")) {
            if (name.equals(category.getName())) {
                if (category.getBalance() < cost) {
                    String err = "Остаток по категории '" + category.getName() + "'" + "составляет: " + category.getBalance() + ". Вы должны указать меньшую сумму";
                    Settings.errorShow(err, getBaseContext());
                } else
                    return true;
            }
        }
        return false;
    }
}