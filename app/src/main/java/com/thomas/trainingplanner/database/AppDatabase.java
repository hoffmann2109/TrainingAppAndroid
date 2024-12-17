package com.thomas.trainingplanner.database;

import android.content.Context;

import androidx.room.*;
@Database(
        entities = {
                Exercise.class,
                ExerciseCompleted.class,
                TrainingDay.class,
                TrainingWeek.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseCompletedDao exerciseCompletedDao();
    public abstract TrainingDayDao trainingDayDao();
    public abstract TrainingWeekDao trainingWeekDao();

    // Singleton-Pattern:
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "training_planner_database"
            ).build();
        }
        return instance;
    }
}