package com.moneysaver;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.ExpensePackage.AddExpense;
import com.moneysaver.ExpensePackage.Expense;
import com.moneysaver.GoalPackge.Goal;
import com.moneysaver.Settings.Category;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.moneysaver.Config.dbName;

public class SQLite {

    public static SQLiteDatabase getDataBase(Context context) {
        return context.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
    }

    public static ArrayList<Goal> getGoalList(Context context) {
        ArrayList<Goal> list = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery("SELECT * FROM Goal;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(new Goal(cursor.getString(1),cursor.getDouble(2), cursor.getDouble(3)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public static ArrayList<Credit> getCreditList(Context context) {
        ArrayList<Credit> list = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery("SELECT * FROM Credit;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(new Credit(cursor.getString(0),cursor.getDouble(1), cursor.getDouble(2), cursor.getString(3)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public static ArrayList<Expense> getExpenseList(Context context) {
        ArrayList<Expense> list = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery("SELECT * FROM Expense;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                Date date;
                try {
                    date = AddExpense.format.parse(cursor.getString(3));
                } catch (Exception e) {
                    continue;
                }
                String notes = "";
                try {
                    notes = cursor.getString(4);
                } catch (Exception e) {
                    notes = "";
                }
                list.add(new Expense(cursor.getString(0),
                        cursor.getDouble(1),
                        date,
                        cursor.getString(2),
                        notes));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public static ArrayList<Category> getCategoryList(Context context, String name) {
        ArrayList<Category> list = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery("SELECT * FROM " + name + ";", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(new Category(cursor.getString(0),cursor.getInt(1), cursor.getDouble(2)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public static String[] getCategoryNames(Context context, String name) {
        ArrayList<Category> categories = getCategoryList(context, name);
        String[] names = new String[categories.size()];
        int it = 0;
        for (Category category: categories) {
            names[it] = category.getName();
            it++;
        }
        return names;
    }

    public static double getBalance(Context context) {
        double balance;
        Cursor cursor;
        SQLiteDatabase db = getDataBase(context);
        cursor = db.rawQuery("SELECT * FROM Balance;", null);
        cursor.moveToFirst();
        balance = cursor.getInt(0);
        cursor.close();
        return balance;
    }

    public static void AddExpense(Context context, Expense expense) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("INSERT INTO Expense (Name, Cost, Category, Data, Notes) VALUES('"
                + expense.getName()
                + "'," + expense.getCost()
                + ", '" + expense.getCategory()
                + "', '" + expense.getDate()
                + "', '" + expense.getNotes()
                + "');");
        db.close();
    }

    public static void deleteAllCategories(Context context, String name) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("DELETE FROM "+ name + ";");
        db.close();
    }

    public static void deleteCredit(Context context, String name) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("DELETE FROM Credit WHERE Name = '"+ name + "';");
        db.close();
    }

    public static void setBalance(Context context, double value) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("DELETE FROM Balance;");
        db.execSQL("INSERT INTO Balance (Balance) VALUES(" + value + ");");
        db.close();
    }

    public static void updateBalance(Context context, double value, int type) {
        double balance = getBalance(context);
        switch (type) {
            case 0:
                setBalance(context, value);
                break;
            case 1:
                setBalance(context, balance + value);
                break;
            case 2:
                setBalance(context, balance - value);
                break;
        }
    }

    public static void updateCategory(Context context, Expense expense) {
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM Category WHERE Title = '" +
                expense.getCategory() + "';", null);
        cursor.moveToFirst();
        double newSpent = cursor.getDouble(2) + expense.getCost();
        db.execSQL("UPDATE Category SET Spent ='"+ newSpent + "' WHERE Title = '" + expense.getCategory() + "';");
        cursor.close();
        db.close();
    }

    public static void updateCredit(Context context, String name, Credit credit) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("UPDATE Credit SET Name ='"+ credit.getName() +
                "', AllSum = " + credit.getAllSum() +
                ", Payout = " + credit.getPayout() +
                ", Notes = '" + credit.getNotes() +
                "' WHERE Name = '" + name + "';");
        db.close();
    }

    public static void addCredit(Context context, Credit credit) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("INSERT INTO Credit (Name, AllSum, Payout, Notes) VALUES('" + credit.getName()
                + "'," + credit.getAllSum() + ", " + credit.getPayout() +
                ",'" + credit.getNotes() + "');");
        db.close();
    }

    public static void saveCategories(Context context, ArrayList<Category> listToSave, String name) {
        SQLiteDatabase db = getDataBase(context);
        for (Category category: listToSave) {
            if (!category.deleted) {
                db.execSQL("INSERT INTO " + name + " (Title, MaxSum, Spent) VALUES('" + category.getName()
                        + "'," + category.getMaxSum() + ", " + category.getSpent() + ");");
            }
        }
        db.close();
    }

    public static void initialiseDataBase(Context context) {
        SQLiteDatabase db = getDataBase(context);
        db.execSQL("CREATE TABLE IF NOT EXISTS Category ("+
                "Title TEXT NOT NULL," +
                "MaxSum INTEGER NOT NULL," +
                "Spent DOUBLE NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS SaveCategories ("+
                "Title TEXT NOT NULL," +
                "MaxSum INTEGER NOT NULL," +
                "Spent DOUBLE NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Goal ("+
                "Name TEXT NOT NULL," +
                "AllSum DOUBLE NOT NULL," +
                "Saved DOUBLE NOT NULL," +
                "Notes TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Credit ("+
                "Name TEXT NOT NULL," +
                "AllSum DOUBLE NOT NULL," +
                "Payout DOUBLE NOT NULL," +
                "Notes TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Expense ("+
                "Name TEXT NOT NULL," +
                "Cost DOUBLE NOT NULL," +
                "Category TEXT NOT NULL," +
                "Data TEXT NOT NULL," +
                "Notes TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Balance (Balance DOUBLE);");
        tryAddBaseInfo(db);
    }

    /*
    Check if database contains default information
     */
    private static void tryAddBaseInfo(SQLiteDatabase db) {
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM Balance;", null);
        if (!(cursor.getCount() > 0))
            db.execSQL("INSERT INTO Balance (Balance) VALUES(0);");
        cursor.close();
    }
}
