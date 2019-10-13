package com.moneysaver;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moneysaver.Settings.Category;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends ListFragment {
    private List<String> categories = new ArrayList<String>();
    private List<String> spent = new ArrayList<String>();
    private List<String> cost = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Category> listCategories = SQLite.getCategoryList(getContext(), "Category");
        for (Category category: listCategories) {
            categories.add(category.getName());
            double value = category.getMaxSum() - category.getSpent();
            cost.add(Double.toString(value));
            spent.add(Double.toString(category.getSpent()));
        }

        View view = inflater.inflate(R.layout.content_main, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, categories);
        setListAdapter(adapter);
        return view;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // заменяем текст в другом фрагменте по нажатию на элемент списка
        DetailsFragment detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailsfragment);
        detailsFragment.change(cost.get(position), spent.get(position));
        getListView().setSelector(R.color.colorBackground);
    }
}