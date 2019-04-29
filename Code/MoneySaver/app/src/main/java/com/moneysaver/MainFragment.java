package com.moneysaver;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainFragment extends ListFragment {
    String[] categories = new String[]{
            "Еда", "Развлечения", "Долги", "Прочее", "Услуги", "Бар", "Игры"
    };

    String[] cost = new String[]{
            "500", "600", "700", "300", "333", "800", "1000"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        detailsFragment.change(categories[position], cost[position]);
        getListView().setSelector(android.R.color.holo_blue_bright);
    }
}
