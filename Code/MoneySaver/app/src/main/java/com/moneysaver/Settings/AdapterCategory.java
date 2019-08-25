package com.moneysaver.Settings;

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
    private String[] categoryNames;
    private ArrayList<Category> categories;
    private TextView balanceForCategories;

    public AdapterCategory(String[] categoryNames, TextView balanceForCategories){
        this.categoryNames = categoryNames;
        this.balanceForCategories = balanceForCategories;
        categories = new ArrayList<>();
        for (String category: categoryNames)
            categories.add(new Category(category, 0));
    }

    @Override
    public int getCount() {
        return categoryNames.length;
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

        //TextWatcher. It is need to check when user change maxSum
        TextWatcherBalance inputTextWatcher = new TextWatcherBalance(newCategoryMaxSum, balanceForCategories);
        newCategoryMaxSum.addTextChangedListener(inputTextWatcher);

        return view;
    }
}
