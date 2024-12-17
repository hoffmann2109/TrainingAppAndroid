package com.thomas.trainingplanner.entities;

public enum MuscleGroup {
    CALVES("Calves"),
    HAMSTRINGS("Hamstrings"),
    QUADS("Quads"),
    CHEST("Chest"),
    BACK("Back"),
    GLUTES("Glutes"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    ABS("Abs"),
    FOREARMS("Forearms"),
    TRAPS("Traps"),
    REARDELTS("Rear Delts"),
    SIDEDELTS("Side Delts"),
    FRONTDELTS("Front Delts");

    private final String name;

    // Constructor
    MuscleGroup(String name) {
        this.name = name;
    }

    // Getter method
    public String getName() {
        return name;
    }
}
