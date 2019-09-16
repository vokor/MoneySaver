package com.moneysaver.Settings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.moneysaver.SQLite;

public class VariableFields {

    public boolean isNewBalanceCorrect;

    public int sumCategories;

    private TextView textBalanceWithCateg;

    private EditText editNewBalance;

    private double baseBalance;

    public VariableFields(EditText eNewBal, TextView tBal, Context context) {
        this.editNewBalance = eNewBal;
        this.textBalanceWithCateg = tBal;
        this.isNewBalanceCorrect = true;
        this.baseBalance = SQLite.getBalance(context);
        setListenerOnNewBalance();
    }

    public void recountBalance() {
        double balance = getUserBalance();
        if (balance >= sumCategories) {
            editNewBalance.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            isNewBalanceCorrect = true;
            textBalanceWithCateg.setText(Double.toString(balance - sumCategories));
        }
        else
        {
            editNewBalance.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            isNewBalanceCorrect = false;
            textBalanceWithCateg.setText(Double.toString(baseBalance - sumCategories));
        }
    }

    public double getUserBalance(){
        String str = editNewBalance.getText().toString();
        if (str.equals(""))
            return baseBalance;
        try{
            int b = Integer.parseInt(str);
            if (b < 0)
                return -2.0;
            else
                return b;
        }catch(NumberFormatException e){
            return -2.0;
        }
    }

    //Listen when new balance change
    private void setListenerOnNewBalance() {
        editNewBalance.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                double newBalance = getUserBalance();
                if (newBalance > 0) {
                    if (newBalance < sumCategories) {
                        editNewBalance.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        isNewBalanceCorrect = false;
                    }
                    else {
                        textBalanceWithCateg.setText(Double.toString(newBalance - sumCategories));
                        editNewBalance.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        isNewBalanceCorrect = true;
                        return false;
                    }
                }
                else {
                    editNewBalance.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    isNewBalanceCorrect = false;
                }
                textBalanceWithCateg.setText(Double.toString(baseBalance - sumCategories));
                return false;
            }
        });
    }

    /*
    Initialize:
        1) sumCategories = sum all balances from exists categories
        2) Set value of balance without categories. Simply difference between baseBalance (balance from DTB) and sumCategories
     */
    public void setBalanceWithoutCategories() {
        double userBalance = getUserBalance();
        if (userBalance < 0)
            userBalance = baseBalance;
        textBalanceWithCateg.setText(Double.toString(userBalance - sumCategories));
    }

    public void setSum(int sum) {
        sumCategories = sum;
    }
}
