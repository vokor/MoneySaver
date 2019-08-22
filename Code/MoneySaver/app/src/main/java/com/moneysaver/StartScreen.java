package com.moneysaver;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.moneysaver.Config.dbName;

public class StartScreen extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        initialiseDataBase();

        Button startButton = findViewById(R.id.button);
        View.OnClickListener listenerCreate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, MainActivity.class);
                startActivity(intent);
            }
        };
        startButton.setOnClickListener(listenerCreate);
    }

    /**
     * Initialise SQLite Data Base with default information
     */
    private void initialiseDataBase() {
        db = getBaseContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        //TODO: check database to contain Category (must be 3 columns)
        db.execSQL("CREATE TABLE IF NOT EXISTS Category ("+
                "Title TEXT NOT NULL," +
                "MaxSum INTEGER NOT NULL," +
                "Spent INTEGER NOT NULL);");

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

        db.execSQL("CREATE TABLE IF NOT EXISTS Balance (Balance INTEGER);");
        tryAddBaseInfo();
    }

    /*
    Check if database contains default information
     */
    private void tryAddBaseInfo() {
        Cursor cursor;
        for (String category:Config.baseCategories) {
            cursor = db.rawQuery("SELECT * FROM Category WHERE Title = '" +
                    category + "';", null);
            if (!(cursor.getCount() > 0))
                db.execSQL("INSERT INTO Category (Title, MaxSum, Spent) VALUES('" +
                        category + "', 0, 0);");
        }
        cursor = db.rawQuery("SELECT * FROM Balance;", null);
        if (!(cursor.getCount() > 0))
            db.execSQL("INSERT INTO Balance (Balance) VALUES(0);");
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
