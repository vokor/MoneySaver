package com.moneysaver.GoalPackge;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.moneysaver.CreditPackage.ListCredit;
import com.moneysaver.MainActivity;
import com.moneysaver.R;
import com.moneysaver.SQLite;

import java.util.ArrayList;

import static com.moneysaver.Config.dbName;

public class ListGoal extends AppCompatActivity {
    private ListView vListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_goal);
        vListView = findViewById(R.id.goallist_view);
        showListView(SQLite.getGoalList(getBaseContext()));

        Button button_add = findViewById(R.id.button_add);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListGoal.this, AddGoal.class);
                startActivity(intent);
            }
        };
        button_add.setOnClickListener(listener);
    }

    private void showListView(ArrayList<Goal> list){
        final AdapterGoal a = new AdapterGoal(list);
        vListView.setAdapter(a);
        vListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Goal item = (Goal)a.getItem(position);
                Intent intent = new Intent(view.getContext(), GoalView.class);
                intent.putExtra(Goal.class.getSimpleName(), item);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListGoal.this, MainActivity.class);
        startActivity(intent);
    }
}
