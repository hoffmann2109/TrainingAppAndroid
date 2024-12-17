package com.thomas.trainingplanner.entities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Calendar {
    private static LocalDate today = LocalDate.now();

    public static String getTodaysDay(){
        if (today.getDayOfMonth()>9){
            return ""+ today.getDayOfMonth();
        } else {
            return "0" + today.getDayOfMonth();
        }
    }

    public static String calculateDay(String today, int number){
        try {
            int todaysDay = Integer.parseInt(today);
            int newDay = todaysDay + number;
            if  (todaysDay > 9){
                return "" + newDay;
            } else {
                return "0" + newDay;
            }
        } catch (NumberFormatException e){
            return today;
        }
    }

    public static String getCurrentMonth(){
        return today.getMonth().toString();
    }

    public static String getDateAsString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String today = dateFormat.format(new Date());
        return today;
    }

    public static String getDateStringForDay(String buttonDate) {
        try {
            LocalDate currentDate = LocalDate.now();
            int currentDay = currentDate.getDayOfMonth();
            int selectedDay = Integer.parseInt(buttonDate);
            int daysDifference = selectedDay - currentDay;

            LocalDate selectedDate = currentDate.plusDays(daysDifference);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            return dateFormat.format(java.sql.Date.valueOf(selectedDate.toString()));
        } catch (NumberFormatException e) {
            return getDateAsString();
        }
    }

}
