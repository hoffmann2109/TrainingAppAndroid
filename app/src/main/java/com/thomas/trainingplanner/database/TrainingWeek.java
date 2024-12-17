package com.thomas.trainingplanner.database;

import androidx.room.*;
@Entity(tableName = "training_weeks",
        foreignKeys = @ForeignKey(
                entity = TrainingDay.class,
                parentColumns = "id",
                childColumns = "training_day_id"
        ), indices = {@Index("training_day_id")})
public class TrainingWeek {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "week_number")
    private int weekNumber;

    @ColumnInfo(name = "training_day_id")
    private int trainingDayId;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }

    public int getTrainingDayId() { return trainingDayId; }
    public void setTrainingDayId(int trainingDayId) { this.trainingDayId = trainingDayId; }
}

