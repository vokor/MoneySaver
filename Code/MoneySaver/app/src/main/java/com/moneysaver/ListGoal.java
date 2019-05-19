package com.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class ListGoal extends AppCompatActivity {
    ListView vListView;
    ArrayList<Goal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_goal);
        vListView = findViewById(R.id.goallist_view);
        showListView(list);

        Button button_add = findViewById(R.id.button_add);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListGoal.this, AddGoal.class);
                startActivityForResult(i, 2);
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
                Intent intent = new Intent(view.getContext(), Goal.class);
                intent.putExtra(Goal.class.getSimpleName(), item);
                startActivity(intent);
            }
        });
    }
}
