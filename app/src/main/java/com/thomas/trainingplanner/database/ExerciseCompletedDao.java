package com.thomas.trainingplanner.database;

import androidx.room.*;

import java.util.List;

@Dao
public interface ExerciseCompletedDao {
    @Query("SELECT * FROM exercises_completed WHERE exercise_id = :exerciseId")
    List<ExerciseCompleted> getCompletedExercises(int exerciseId);

    @Insert
    void insert(ExerciseCompleted exerciseCompleted);

    @Update
    void update(ExerciseCompleted exerciseCompleted);

    @Delete
    void delete(ExerciseCompleted exerciseCompleted);
}
