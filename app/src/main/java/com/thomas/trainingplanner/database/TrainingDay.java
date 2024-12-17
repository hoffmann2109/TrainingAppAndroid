package com.thomas.trainingplanner.database;

import androidx.room.*;

@Entity(tableName = "training_days",
        foreignKeys = @ForeignKey(
                entity = Exercise.class,
                parentColumns = "id",
                childColumns = "exercise_id"
        ), indices = {@Index("exercise_id")})
public class TrainingDay {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "exercise_id")
    private int exerciseId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

}