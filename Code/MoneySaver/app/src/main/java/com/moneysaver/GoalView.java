package com.moneysaver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GoalView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_view);
        Goal goal = (Goal) getIntent().getSerializableExtra(Goal.class.getSimpleName());
        TextView name = findViewById(R.id.textViewName);
        name.setText(goal.getName());
        TextView cost = findViewById(R.id.textViewCost);
        cost.setText(goal.getSaved() + "/" + goal.getCost());
        TextView notes = findViewById(R.id.textViewNotes);
        notes.setText(goal.getNotes());
    }
}