package com.moneysaver.Settings;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.moneysaver.R;

import java.util.ArrayList;

class SettingsAdapter extends ArrayAdapter<Category> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Category> categoryList;

    SettingsAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categoryList = categories;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Category product = categoryList.get(position);

        viewHolder.nameView.setText(product.getName());

        viewHolder.actionButton1.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.actionButton1.type) {
                    viewHolder.actionButton1.type = false;
                    viewHolder.actionButton1.b.setText("Изменить");
                    viewHolder.actionButton1.b.setBackgroundColor(Color.parseColor("#0000FF"));
                }
                else
                {
                    viewHolder.actionButton1.b.setText("Изменено");
                    viewHolder.actionButton1.b.setBackgroundColor(Color.parseColor("#008000"));;
                    viewHolder.actionButton1.type = true;
                }
            }
        });

        viewHolder.actionButton2.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.actionButton2.type) {
                    viewHolder.actionButton2.type = false;
                    viewHolder.actionButton2.b.setText("Удалить");
                    viewHolder.actionButton1.b.setEnabled(true);
                    viewHolder.actionButton2.b.setBackgroundColor(Color.parseColor("#0000FF"));
                }
                else
                {
                    viewHolder.actionButton2.b.setText("Удалено");
                    viewHolder.actionButton1.b.setEnabled(false);
                    viewHolder.actionButton2.b.setBackgroundColor(Color.parseColor("#FF0000"));;
                    viewHolder.actionButton2.type = true;
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final MyButton actionButton1;
        final MyButton actionButton2;
        final TextView nameView;
        ViewHolder(View view){
            actionButton1 = new MyButton(view, true);
            actionButton1.b.setText("Изменить");
            actionButton1.b.setBackgroundColor(Color.parseColor("#0000FF"));

            actionButton2 = new MyButton(view, false);
            actionButton2.b.setText("Удалить");
            actionButton2.b.setBackgroundColor(Color.parseColor("#0000FF"));
            nameView = (TextView) view.findViewById(R.id.nameCategory);
        }
    }

    private class MyButton {
        final Button b;
        private boolean type;
        MyButton(View view, boolean flag) {
            if (flag)
                b = view.findViewById(R.id.circularButton1);
            else
                b = view.findViewById(R.id.circularButton2);
            type = false;
        }
    }
}
