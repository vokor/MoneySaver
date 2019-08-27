package com.moneysaver.Settings;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.moneysaver.R;

import java.util.ArrayList;

public class AdapterCategory extends BaseAdapter {
    private ArrayList<Category> categories;
    private TextView balanceForCategories;

    public AdapterCategory(ArrayList<Category> categories, TextView balanceForCategories){
        this.balanceForCategories = balanceForCategories;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Set name of category
        View view = inflater.inflate(R.layout.add_categories_adapter, parent, false);
        TextView newCategoryName = view.findViewById(R.id.newCategoryName);
        newCategoryName.setText(categories.get(position).getName());

        //Set maxSum of category
        EditText newCategoryMaxSum = view.findViewById(R.id.setCategoryMaxSum);
        newCategoryMaxSum.setText(Integer.toString(categories.get(position).getMaxSum()));

        // Set color
        if (categories.get(position).approved)
            newCategoryMaxSum.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        else
            newCategoryMaxSum.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        //TextWatcher. It is need to check when user change maxSum
        TextWatcherBalance inputTextWatcher = new TextWatcherBalance(newCategoryMaxSum, balanceForCategories, categories.get(position));
        newCategoryMaxSum.addTextChangedListener(inputTextWatcher);

        return view;
    }
}
