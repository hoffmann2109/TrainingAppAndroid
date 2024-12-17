package com.thomas.trainingplanner.entities;

public class ExerciseData {
    private int id;
    private String name;
    private float weight;
    private int reps;
    private float rpe;


    public ExerciseData(int id, String name, float weight, int reps, float rpe) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.rpe = rpe;
    }

    // Getters

    public int getId() { return id; }

    public String getName() { return name; }
    public float getWeight() { return weight; }
    public int getReps() { return reps; }
    public float getRpe() { return rpe; }
}
