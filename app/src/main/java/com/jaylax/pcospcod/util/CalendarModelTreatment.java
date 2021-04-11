package com.jaylax.pcospcod.util;

public class CalendarModelTreatment {

    public String date;
    public String day;
    public int number;

    public CalendarModelTreatment(String date, String day, int number)
    {
        this.date = date;
        this.day = day;
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }


    public int getNumber() {
        return number;
    }
}
