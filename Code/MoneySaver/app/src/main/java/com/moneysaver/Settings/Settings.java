package com.moneysaver.Settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.moneysaver.Config;
import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;

import java.util.ArrayList;

import static com.moneysaver.Config.rusSymbols;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private ListView categoryList;

    private MultiAutoCompleteTextView autoCompleteTextView;

    private VariableFields vFields;

    private Container container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        autoCompleteTextView = findViewById(R.id.autocomplete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Config.baseCategories);
        autoCompleteTextView.setAdapter(adapter);

        SaveCategories saveCategories = new SaveCategories(getBaseContext());
        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        saveCategories.setNamesToEdit(autoCompleteTextView);
        setListenerOnListCategories();

        // This is container with all categories (default, saved, changed, deleted)
        container = new Container(SQLite.getCategoryList(getBaseContext(), "Category"), saveCategories);

        vFields = new VariableFields((EditText) findViewById(R.id.newBalance), (TextView) findViewById(R.id.balanceWithoutCategoties), getBaseContext());
        vFields.setSum(container.getSum());
        vFields.setBalanceWithoutCategories();

        categoryList = findViewById(R.id.categoryList);
        SettingsAdapter categoriesAdapter = new SettingsAdapter(this, R.layout.list_categories, vFields, container);
        categoryList.setAdapter(categoriesAdapter);

        final Button saveChanges = findViewById(R.id.saveChanges);
        final Button abortChanges = findViewById(R.id.abortChanges);
        final Button addCategories = findViewById(R.id.addCategories);
        saveChanges.setOnClickListener(this);
        abortChanges.setOnClickListener(this);
        addCategories.setOnClickListener(this);
    }

    private void setListenerOnListCategories() {
        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                autoCompleteTextView.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addCategories:
                //First of all we try to check all input symbols and string pattern.
                String newCategories = autoCompleteTextView.getText().toString();
                if (newCategories.equals("") || !newCategories.matches("([" + rusSymbols + "_]*,( )*)*[" + rusSymbols + "_]*"))
                {
                    String errorMessage = "Не удалось распознать введенные категории. Помните, что название может состоять только из русских букв, цифр и знака _. Используйте запятую для разделения";
                    errorShow(errorMessage, Settings.this);
                    break;
                }
                // Try to check that input names are not present in categories
                String[] splitData = newCategories.replaceAll(" ", "").split(",");
                if (!checkUniqueNames(splitData))
                    break;
                if (!checkNewBalance())
                    break;
                Intent intent = new Intent(Settings.this, AddCategories.class);

                intent.putExtra("array", splitData);
                intent.putExtra("balance", vFields.getUserBalance() - vFields.sumCategories);
                startActivity(intent);
                break;
            case R.id.saveChanges:
                if (!checkNewBalance())
                    break;
                dialogWindow();
                break;
            case R.id.abortChanges:
                container.abortEverithing();
                Intent intent1 = new Intent(Settings.this, MainActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //Check balance which user setted.
    private boolean checkNewBalance() {
        int type = vFields.getUserBalance();
        if (type == -2) {
            String errMessage = "Поле Баланс должно содержать неотрицательное число!";
            errorShow(errMessage, Settings.this);
            return false;
        }
        if (!vFields.isNewBalanceCorrect) {
            String errMessage = "Новый баланс должен быть больше суммы всех имеющихся категорий!";
            errorShow(errMessage, Settings.this);
            return false;
        }
        return true;
    }

    private boolean checkUniqueNames(String[] data) {
        for (int i = 0; i < data.length; i++)
            for (Category category: container.getCommonCategories())
                if (data[i].equals(category.getName())) {
                    String errorMessage = "Название категории '" + category.getName() + "' уже занято. Придумайте другое.";
                    errorShow(errorMessage, Settings.this);
                    return false;
                }
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data.length; j++)
                if (i != j && data[i].equals(data[j])) {
                    String errorMessage = "Название '" + data[i] + "' можно использовать только один раз";
                    errorShow(errorMessage, Settings.this);
                    return false;
                }
        return true;
    }

    public static void errorShow(String errorMessage, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ошибка")
                .setMessage(errorMessage)
                .setIcon(R.drawable.ic_error_black_24dp)
                .setCancelable(false)
                .setPositiveButton("Понятно",
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialogWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Уведомление")
                .setMessage("Сохранить изменения?")
                .setIcon(R.drawable.ic_notifications_active_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Не хочу",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(Settings.this, "Отмена!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Подтверждаю",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                container.saveEverything();
                                SQLite.setBalance(getBaseContext(), vFields.getUserBalance());
                                Toast.makeText(Settings.this, "Сохранено!",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                Intent intent = new Intent(Settings.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}