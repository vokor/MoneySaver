package com.moneysaver.Statistics;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.moneysaver.Settings.Category;

import java.util.ArrayList;

public class ChartListener implements OnChartValueSelectedListener {

    private ArrayList<Category> categories;
    private PieChart chart;

    public ChartListener(ArrayList<Category> categories, PieChart chart) {
        this.categories = categories;
        this.chart = chart;
        PieData pieData = chart.getData();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pieEntry = (PieEntry)e;
        Category category = getCategory(pieEntry.getLabel());
        String viewString = "Потрачено\n" + category.getSpent() + "\nОсталось\n" + category.getBalance();
        chart.setCenterText(viewString);

    }

    @Override
    public void onNothingSelected() {
        chart.setCenterText("Диаграмма категорий");
    }

    private Category getCategory(String name) {
        for (Category category: categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
