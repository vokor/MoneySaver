package com.moneysaver.GoalPackge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.moneysaver.R;

public class DeleteGoal extends AppCompatActivity {
    Button deleteButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_goal);

        deleteButton = findViewById(R.id.deleteButton);
        cancelButton = findViewById(R.id.cancelButton);

        View.OnClickListener listenerCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("button", "cancel");
                setResult(4, i);
                finish();
            }
        };
        cancelButton.setOnClickListener(listenerCancel);

        View.OnClickListener listenerDelete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("button", "delete");
                setResult(4, i);
                finish();
            }
        };
        deleteButton.setOnClickListener(listenerDelete);
    }
}