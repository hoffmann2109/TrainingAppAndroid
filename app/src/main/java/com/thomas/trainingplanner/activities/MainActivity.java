package com.thomas.trainingplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thomas.trainingplanner.R;
import com.thomas.trainingplanner.database.AppDatabase;
import com.thomas.trainingplanner.database.Exercise;
import com.thomas.trainingplanner.database.ExerciseCompleted;
import com.thomas.trainingplanner.entities.ExerciseData;
import com.thomas.trainingplanner.database.TrainingDay;
import com.thomas.trainingplanner.entities.Calendar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExerciseAdapter exerciseAdapter;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize:
        // Find buttons by ID
        Button button1 = findViewById(R.id.calendarButton1);
        Button button2 = findViewById(R.id.calendarButton2);
        Button button3 = findViewById(R.id.calendarButton3);
        Button button4 = findViewById(R.id.calendarButton4);
        Button button5 = findViewById(R.id.calendarButton5);
        // Add buttons to ArrayList:
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);

        selectedDate = Calendar.getDateAsString();

        // Find textView by ID
        TextView textView1 = findViewById(R.id.textView2);
        initialize(buttonList, textView1);

        // Switch to second Activity with add-Button
        addButtonFunctionality();

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.exerciseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(this);
        recyclerView.setAdapter(exerciseAdapter);

        // Load exercises for today
        loadExercisesForDate(selectedDate);
    }

    private void initialize(@NonNull List<Button> buttonList, TextView textView1){
        // Get day of month of today
        String today = Calendar.getTodaysDay();
        String month = Calendar.getCurrentMonth();

        // Set the button texts and the textView text
        for (int i = 0; i < buttonList.size(); i++){
            int size = buttonList.size();
            Button button = buttonList.get(i);
            String buttonDate = Calendar.calculateDay(today, i - size/2);
            if (i == (size / 2)){
                button.setText(today);
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.activeElements));
            } else {
                button.setText(Calendar.calculateDay(today, i - size/2));
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
            }

            // Set OnClickListener with date information
            button.setOnClickListener(v -> {
                buttonBehaviour(buttonList, button);
                // Calculate the full date string for the clicked button
                String clickedDate = Calendar.getDateStringForDay(buttonDate);
                selectedDate = clickedDate;
                loadExercisesForDate(selectedDate);
            });

        }
        textView1.setText(Calendar.getCurrentMonth());
    }

    private void buttonBehaviour(@NonNull List<Button> buttonList, Button clickedButton) {
        for (Button button : buttonList) {
            if (button == clickedButton) {
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.activeElements));
            } else {
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
            }
        }
        loadExercisesForDate(selectedDate);
    }

    private void addButtonFunctionality(){
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddButtonActivity.class);
            startActivity(intent);
        });
    }

    private void loadExercisesForDate(String selectedDate) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            // Custom query to get today's exercises with all details
            List<ExerciseData> exerciseDataList = new ArrayList<>();

            List<TrainingDay> todaysTraining = db.trainingDayDao()
                    .getTrainingDaysByDate(selectedDate);

            for (TrainingDay trainingDay : todaysTraining) {
                Exercise exercise = db.exerciseDao()
                        .getExerciseById(trainingDay.getExerciseId());

                List<ExerciseCompleted> completed = db.exerciseCompletedDao()
                        .getCompletedExercises(exercise.getId());

                for (ExerciseCompleted comp : completed) {
                    exerciseDataList.add(new ExerciseData(
                            exercise.getId(),
                            exercise.getName(),
                            comp.getWeight(),
                            comp.getReps(),
                            comp.getRpe()
                    ));
                }
            }

            // Update UI on main thread
            runOnUiThread(() -> {
                exerciseAdapter.setExercises(exerciseDataList);
            });
        }).start();
    }

}