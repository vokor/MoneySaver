package com.moneysaver.Settings;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class TextWatcherBalance implements TextWatcher {
    private EditText editText;
    private TextView balanceForCategories;
    private Category category;

    public TextWatcherBalance(EditText et, TextView balanceForCategories, Category category) {
        super();
        this.editText = et;
        this.balanceForCategories = balanceForCategories;
        this.category = category;
        this.category.approved = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = editText.getText().toString();
        double appBalance;
        if (category.approved)
            appBalance = Double.parseDouble(balanceForCategories.getText().toString()) + category.getMaxSum();
        else
            appBalance = Double.parseDouble(balanceForCategories.getText().toString());
        balanceForCategories.setText(Double.toString(appBalance));
        double maxBalance = checkStr(str, appBalance);
        if (!category.approved) {
            editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            return;
        }
        editText.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        balanceForCategories.setText(Double.toString(appBalance - maxBalance));
    }

    private double checkStr(String str, double appBalance) {
        try{
            int number = Integer.parseInt(str);
            if (number < 0 || number > appBalance)
                category.approved = false;
            else
                category.approved = true;
            category.setMaxSum(number);
            return number;
        }catch(NumberFormatException e){
            category.approved = false;
            return 0;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
        /*if (!category.approved) {
            category.setMaxSum(0);
            return;
        }
        String str = editText.getText().toString();
        try{
            int number = Integer.parseInt(str);
            if (number < 0)
                category.setMaxSum(0);
            category.setMaxSum(number);
        }catch(NumberFormatException e){
            category.setMaxSum(0);
        }*/
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
}
