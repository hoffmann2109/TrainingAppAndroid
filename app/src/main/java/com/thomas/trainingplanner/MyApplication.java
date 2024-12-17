package com.thomas.trainingplanner;

import android.app.Application;

import com.thomas.trainingplanner.database.AppDatabase;

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        // Initialize database:
        AppDatabase.getInstance(getApplicationContext());
    }
}
