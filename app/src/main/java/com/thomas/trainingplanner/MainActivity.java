package com.thomas.trainingplanner;

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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        // Find textView by ID
        TextView textView1 = findViewById(R.id.textView2);
        initialize(buttonList, textView1);


    }

    private void initialize(@NonNull List<Button> buttonList, TextView textView1){
        // Get day of month of today
        String today = Calendar.getToday();
        String month = Calendar.getCurrentMonth();

        // Set the button texts and the textView text
        for (int i = 0; i < buttonList.size(); i++){
            int size = buttonList.size();
            Button button = buttonList.get(i);
            if (i == (size / 2)){
                button.setText(today);
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.activeElements));
            } else {
                button.setText(Calendar.calculateDay(today, i - size/2));
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
            }

            // Set OnClickListener for each button
            button.setOnClickListener(v -> {
                buttonBehaviour(buttonList, button);
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
    }

}