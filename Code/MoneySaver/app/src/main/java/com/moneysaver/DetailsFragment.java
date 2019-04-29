package com.moneysaver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    TextView categories, cost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment, container, false);
        categories = (TextView) view.findViewById(R.id.languages);
        cost = (TextView) view.findViewById(R.id.versions);

        return view;
    }

    // заменяем текст в TextView этого фрагмента
    public void change(String text, String text2) {
        categories.setText(text);
        cost.setText(text2);
    }
}