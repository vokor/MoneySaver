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

}
