package com.moneysaver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static android.content.Context.MODE_PRIVATE;
import static com.moneysaver.Config.dbName;

public class MainFragment extends ListFragment {
    private List<String> categories = new ArrayList<String>();
    private List<String> balance = new ArrayList<String>();
    private List<String> cost = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SQLiteDatabase db;
        db = getContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Category;", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                categories.add(cursor.getString(1));
                cost.add(Integer.toString(cursor.getInt(2)));
                int value = cursor.getInt(3) - cursor.getInt(2);
                balance.add(Integer.toString(value));
            } while (cursor.moveToNext());
            cursor.close();
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
        detailsFragment.change(cost.get(position), balance.get(position));
        getListView().setSelector(android.R.color.holo_blue_bright);
    }
}