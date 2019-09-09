package com.moneysaver.CreditPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.moneysaver.CreditPackage.Credit;
import com.moneysaver.R;

public class CreditView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_view);
        Credit credit = (Credit) getIntent().getSerializableExtra(Credit.class.getSimpleName());
        TextView name = findViewById(R.id.textViewName);
        name.setText(credit.getName());
        TextView cost = findViewById(R.id.textViewCost);
        cost.setText(credit.getPayout() + "/" + credit.getAllSum());
        TextView notes = findViewById(R.id.textViewNotes);
        notes.setText(credit.getNotes());
    }
}