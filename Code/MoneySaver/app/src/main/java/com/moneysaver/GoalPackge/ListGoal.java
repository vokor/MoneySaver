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
    private Goal choosenGoal;

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
                choosenGoal = item;
                Intent intent = new Intent(view.getContext(), GoalView.class);
                intent.putExtra(Goal.class.getSimpleName(), item);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListGoal.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    String b = data.getStringExtra("button");
                    switch (b) {
                        case "ok":
                            break;
                        case "edit": {
                            Intent intent = new Intent(ListGoal.this, EditGoal.class);
                            intent.putExtra(Goal.class.getSimpleName(), choosenGoal);
                            startActivityForResult(intent, 3);
                            break;
                        }
                        case "delete": {
                            Intent intent = new Intent(ListGoal.this, DeleteGoal.class);
                            startActivityForResult(intent, 4);
                            break;
                        }
                        case "saveMoney": {

                        }
                    }
                }
                case 2: {
                    // создать цель
                }
                case 3: {
                    // edit goal
                }
                case 4: {
                    String b = data.getStringExtra("button");
                    if(b.equals("delete")){
                        // delete goal
                    }
                }
                case 5: {
                //save money
                }

                }
            }
        }
}
