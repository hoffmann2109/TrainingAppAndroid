package com.thomas.trainingplanner.database;

import androidx.room.*;
import java.util.List;
@Dao
public interface TrainingWeekDao {
    @Query("SELECT * FROM training_weeks WHERE week_number = :weekNumber")
    List<TrainingWeek> getTrainingWeekByNumber(int weekNumber);

    @Insert
    void insert(TrainingWeek trainingWeek);

    @Update
    void update(TrainingWeek trainingWeek);

    @Delete
    void delete(TrainingWeek trainingWeek);
}
