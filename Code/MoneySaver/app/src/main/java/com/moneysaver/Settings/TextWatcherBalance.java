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
    private int prevBalance;
    private boolean isPrevApprove;

    public TextWatcherBalance(EditText et, TextView balanceForCategories) {
        super();
        this.editText = et;
        this.balanceForCategories = balanceForCategories;
        prevBalance = 0;
        isPrevApprove = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = editText.getText().toString();
        int appBalance = Integer.parseInt(balanceForCategories.getText().toString()) + prevBalance;
        balanceForCategories.setText(Integer.toString(appBalance));
        int maxBalance = checkStr(str, appBalance);
        if (maxBalance == -1) {
            editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            return;
        }
        editText.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        balanceForCategories.setText(Integer.toString(appBalance - maxBalance));
        isPrevApprove = true;
    }

    private int checkStr(String str, int appBalance) {
        try{
            int number = Integer.parseInt(str);
            if (number < 0 || number > appBalance) {
                isPrevApprove = false;
                return -1;
            }
            return number;
        }catch(NumberFormatException e){
            return -1;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
        if (!isPrevApprove) {
            prevBalance = 0;
            return;
        }
        String str = editText.getText().toString();
        try{
            int number = Integer.parseInt(str);
            if (number < 0)
                prevBalance = 0;
            prevBalance = number;
        }catch(NumberFormatException e){
            prevBalance = 0;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
}
