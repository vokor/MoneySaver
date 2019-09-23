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
import android.widget.Spinner;

import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;
import com.moneysaver.Settings.Category;
import com.moneysaver.Settings.Settings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddExpense extends AppCompatActivity {
    private EditText name;
    private EditText cost;
    private EditText date;
    private EditText notes;
    private Spinner category;
    private Button createButton;
    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        name = findViewById(R.id.nameExpenseEdit);
        StartCostListener();
        date = findViewById(R.id.dateExpenseEdit);
        date.setText(AddExpense.format.format(Calendar.getInstance().getTime()));
        notes = findViewById(R.id.notesExpenseEdit);
        category = findViewById(R.id.categoryExpenseSpinnerAdd);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SQLite.getCategoryNames(getBaseContext(), "Category"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        StartButtonListener();
    }


    private void StartButtonListener() {
        createButton = findViewById(R.id.createExpenseButton);

        createButton.setEnabled(false);

        View.OnClickListener listenerCreate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateStr = date.getText().toString();
                String nameStr = "";
                double costStr = Double.parseDouble(cost.getText().toString());
                String notesStr = "";
                if (!name.isDirty()) {
                    nameStr = name.getText().toString();
                }
                if (!notes.isDirty()) {
                    notesStr = notes.getText().toString();
                }
                String categoryStr = category.getSelectedItem().toString();

                if (!checkInfo(dateStr, categoryStr, costStr)) {
                    return;
                }
                Expense expense = null;
                try {
                    expense = new Expense(nameStr, costStr, format.parse(dateStr), categoryStr, notesStr);
                } catch (Exception e) {
                    return;
                }
                SQLite.AddExpense(AddExpense.this, expense);
                SQLite.updateCategory(AddExpense.this, expense);
                SQLite.updateBalance(AddExpense.this, expense.getCost(), 2);
                Intent intent = new Intent(AddExpense.this, ListExpense.class);
                startActivity(intent);
            }
        };
        createButton.setOnClickListener(listenerCreate);
    }

    private void StartCostListener() {
        cost = findViewById(R.id.costExpenseEdit);

        cost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!(cost.getText().toString().equals(""))){
                    createButton.setEnabled(true);
                }
                else{
                    createButton.setEnabled(false);
                }
            }});

    }

    private boolean checkInfo(String date, String name, double cost) {
        try {
            format.parse(date);
        } catch (Exception e) {
            String err = "Недопустимый формат даты. Дата оформляется по шаблону: 'dd.MM.yyyy'";
            Settings.errorShow(err, AddExpense.this);
            return false;
        }
        for (Category category: SQLite.getCategoryList(getBaseContext(), "Category")) {
            if (name.equals(category.getName())) {
                if (category.getBalance() < cost) {
                    String err = "Остаток по категории '" + category.getName() + "'" + " составляет: " + category.getBalance() + ". Вы должны указать меньшую сумму";
                    Settings.errorShow(err, AddExpense.this);
                    return false;
                } else
                    return true;
            }
        }
        return false;
    }
}
