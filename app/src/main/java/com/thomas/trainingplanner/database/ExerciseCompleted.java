package com.thomas.trainingplanner.database;

import androidx.room.*;

@Entity(tableName = "exercises_completed",
foreignKeys = @ForeignKey(
        entity = Exercise.class,
        parentColumns = "id",
        childColumns = "exercise_id"
), indices = {@Index("exercise_id")})
public class ExerciseCompleted {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "weight")
    private float weight;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "rpe")
    private float rpe;

    @ColumnInfo(name = "exercise_id")
    private int exerciseId;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public float getRpe() { return rpe; }
    public void setRpe(float rpe) { this.rpe = rpe; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
}
