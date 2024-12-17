package com.thomas.trainingplanner.database;

import androidx.room.*;
import java.util.List;
@Dao
public interface TrainingDayDao {
    @Query("SELECT * FROM training_days WHERE date = :date")
    List<TrainingDay> getTrainingDaysByDate(String date);

    @Query("SELECT * FROM training_days WHERE exercise_id = :exerciseId")
    List<TrainingDay> getTrainingDaysByExerciseId(int exerciseId);

    @Insert
    void insert(TrainingDay trainingDay);

    @Update
    void update(TrainingDay trainingDay);

    @Delete
    void delete(TrainingDay trainingDay);
}
