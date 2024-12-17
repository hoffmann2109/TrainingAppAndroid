package com.thomas.trainingplanner.database;

import androidx.room.*;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    List<Exercise> getAllExercises();

    @Query("SELECT * FROM exercises WHERE id = :id")
    Exercise getExerciseById(int id);

    @Query("SELECT * FROM exercises WHERE name = :name LIMIT 1")
    Exercise getExerciseByName(String name);


    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);
}
