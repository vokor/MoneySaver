package com.moneysaver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Config {

    public static final String dbName = "moneysaver.db";

    public static String[] baseCategories = {"Еда", "Транспорт","Здоровье","Развлечения", "Платежи", "Другое"};

    public static String rusSymbols = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗФЫВАПРОЛДЖЭЪЯЧСМИТЬБЮЁ0123456789";

    public static int getBalance(SQLiteDatabase db) {
        int balance;
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM Balance;", null);
        cursor.moveToFirst();
        balance = cursor.getInt(0);
        cursor.close();
        return balance;
    }

    public static void setBalance(SQLiteDatabase db, int value) {
        db.execSQL("DELETE FROM Balance;");
        db.execSQL("INSERT INTO Balance (Balance) VALUES(" + value + ");");
    }
}
