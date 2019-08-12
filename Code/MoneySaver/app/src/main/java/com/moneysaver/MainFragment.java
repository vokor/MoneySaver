package com.moneysaver;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moneysaver.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends ListFragment {
    static List<String> categories = new ArrayList<String>();

    static List<String> cost = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*if (categories.isEmpty()) {
            categories.add("Еда");
            categories.add("Развлечения");
            categories.add("Долги");
            cost.add("100");
            cost.add("200");
            cost.add("300");
        }*/
        // устанавливаем макет
        View view = inflater.inflate(R.layout.content_main, container, false);
        // создаем простой ArrayAdapter со стандартным макетом и входными данными
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, categories);
        setListAdapter(adapter);
        return view;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // заменяем текст в другом фрагменте по нажатию на элемент списка
        DetailsFragment detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailsfragment);
        detailsFragment.change(categories.get(position), cost.get(position));
        getListView().setSelector(android.R.color.holo_blue_bright);
    }
}