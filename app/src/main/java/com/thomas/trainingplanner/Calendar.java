package com.thomas.trainingplanner;

import java.time.LocalDate;

public class Calendar {
    private static LocalDate today = LocalDate.now();

    public static String getToday(){
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
            return "" + newDay;
        } catch (NumberFormatException e){
            return today;
        }
    }

    public static String getCurrentMonth(){
        return today.getMonth().toString();
    }

}
