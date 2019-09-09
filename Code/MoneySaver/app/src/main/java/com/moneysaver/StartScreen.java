package com.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

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
        SQLite.initialiseDataBase(getBaseContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: delete dataBase
    }
}
