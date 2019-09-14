package com.moneysaver;

public class Config {

    public static final String dbName = "moneysaver.db";

    public static String[] baseCategories = {"Еда", "Транспорт","Здоровье","Развлечения", "Платежи", "Другое"};

    public static String rusSymbols = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗФЫВАПРОЛДЖЭЪЯЧСМИТЬБЮЁ0123456789";

    public static final double EPS = 0.0000001;

    private static int actualId = 0;

    public static int getId() {
        actualId += 1;
        return actualId;
    }

    public static double setSpent(double value) {
        return (double) Math.round(value * 10) / 10;
    }
}
