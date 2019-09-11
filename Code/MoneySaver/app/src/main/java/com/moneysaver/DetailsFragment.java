package com.moneysaver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    TextView balance, spent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment, container, false);
        balance = view.findViewById(R.id.balance);
        spent = view.findViewById(R.id.spent);
        return view;
    }

    public void change(String text, String text2) {
        balance.setText(text);
        spent.setText(text2);
    }
}