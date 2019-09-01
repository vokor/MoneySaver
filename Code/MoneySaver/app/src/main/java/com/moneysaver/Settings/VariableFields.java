package com.moneysaver.Settings;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static com.moneysaver.Config.getBalance;

public class VariableFields {

    public boolean isNewBalanceCorrect;

    public int sumCategories;

    private TextView textBalanceWithCateg;

    private EditText editNewBalance;

    private SQLiteDatabase db;

    private int baseBalance;

    public VariableFields(EditText eNewBal, TextView tBal, SQLiteDatabase db) {
        this.editNewBalance = eNewBal;
        this.textBalanceWithCateg = tBal;
        this.isNewBalanceCorrect = true;
        this.db = db;
        this.baseBalance = getBalance(db);
        setListenerOnNewBalance();
    }

    public void recountBalance(int sumCategories) {
        int balance = getUserBalance();
        if (balance > sumCategories) {
            editNewBalance.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            isNewBalanceCorrect = true;
            textBalanceWithCateg.setText(Integer.toString(balance - sumCategories));
        }
    }

    public int getUserBalance(){
        String str = editNewBalance.getText().toString();
        if (str.equals(""))
            return baseBalance;
        try{
            int b = Integer.parseInt(str);
            if (b < 0)
                return -2;
            else
                return b;
        }catch(NumberFormatException e){
            return -2;
        }
    }

    //Listen when new balance change
    private void setListenerOnNewBalance() {
        editNewBalance.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                int newBalance = getUserBalance();
                if (newBalance > 0) {
                    if (newBalance < sumCategories) {
                        editNewBalance.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        isNewBalanceCorrect = false;
                    }
                    else {
                        textBalanceWithCateg.setText(Integer.toString(newBalance - sumCategories));
                        editNewBalance.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        isNewBalanceCorrect = true;
                        return false;
                    }
                }
                else {
                    editNewBalance.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    isNewBalanceCorrect = false;
                }
                textBalanceWithCateg.setText(Integer.toString(baseBalance - sumCategories));
                return false;
            }
        });
    }

    /*
    Initialize:
        1) sumCategories = sum all balances from exists categories
        2) Set value of balance without categories. Simply difference between baseBalance (balance from DTB) and sumCategories
     */
    public void setBalanceWithoutCategories(int sumCategories) {
        int userBalance = getUserBalance();
        if (userBalance > 0)
            userBalance = baseBalance;
        textBalanceWithCateg.setText(Integer.toString(userBalance - sumCategories));
    }
}
