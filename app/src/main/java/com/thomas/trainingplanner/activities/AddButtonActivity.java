package com.thomas.trainingplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.thomas.trainingplanner.R;
import com.thomas.trainingplanner.database.AppDatabase;
import com.thomas.trainingplanner.database.Exercise;
import com.thomas.trainingplanner.database.ExerciseCompleted;
import com.thomas.trainingplanner.database.TrainingDay;
import com.thomas.trainingplanner.entities.Calendar;

public class AddButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_button);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons:
        backButton();
        prefillDate();
        submitButton();
    }

    private void backButton(){
        Button addButton = findViewById(R.id.backButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddButtonActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void prefillDate(){
        EditText dateInput = findViewById(R.id.editTextDate);
        dateInput.setText(Calendar.getDateAsString());
    }

    private void submitButton() {
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            submitDataToDataBase();
        });
    }

    private void submitDataToDataBase(){
        if (!validateInput()){
            return;
        }
        TextInputEditText exerciseName = findViewById(R.id.textInputEditText);
        EditText weight = findViewById(R.id.editTextNumber);
        EditText reps = findViewById(R.id.editTextNumber2);
        EditText rpe = findViewById(R.id.editTextNumber3);
        EditText date = findViewById(R.id.editTextDate);

        // Get the values from the input fields
        String exerciseNameStr = exerciseName.getText().toString();
        float weightFloat = Float.parseFloat(weight.getText().toString());
        int repsInt = Integer.parseInt(reps.getText().toString());
        float rpeFloat = Float.parseFloat(rpe.getText().toString());
        String dateStr = date.getText().toString();

        // Run database operations on background thread
        new Thread(() -> {
            // Get database instance
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());

            // First create and save the exercise
            Exercise exercise = new Exercise();
            exercise.setName(exerciseNameStr);
            //TODO: add a muscle group
            /*
            // You might want to add muscle group here
            exercise.setMuscleGroup(""); // Add appropriate muscle group if needed
            */
            db.exerciseDao().insert(exercise);

            // Query to get the exercise we just inserted
            Exercise insertedExercise = db.exerciseDao()
                    .getExerciseByName(exerciseNameStr);  // You'll need to add this query

            if (insertedExercise != null) {
                // Create and save the training day
                TrainingDay trainingDay = new TrainingDay();
                trainingDay.setDate(dateStr);
                trainingDay.setExerciseId(insertedExercise.getId());
                db.trainingDayDao().insert(trainingDay);

                // Create and save the completed exercise
                ExerciseCompleted completed = new ExerciseCompleted();
                completed.setExerciseId(insertedExercise.getId());
                completed.setWeight(weightFloat);
                completed.setReps(repsInt);
                completed.setRpe(rpeFloat);
                db.exerciseCompletedDao().insert(completed);

                // Return to main thread to handle UI updates
                runOnUiThread(() -> {
                    Toast.makeText(this, "Exercise saved successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddButtonActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        }).start();
    }

    private boolean validateInput() {
        TextInputEditText exerciseName = findViewById(R.id.textInputEditText);
        EditText weight = findViewById(R.id.editTextNumber);
        EditText reps = findViewById(R.id.editTextNumber2);
        EditText rpe = findViewById(R.id.editTextNumber3);

        if (exerciseName.getText().toString().trim().isEmpty()) {
            exerciseName.setError("Exercise name is required");
            return false;
        }

        try {
            Float.parseFloat(weight.getText().toString());
            Integer.parseInt(reps.getText().toString());
            Float.parseFloat(rpe.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}