package com.thomas.trainingplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        initializeButtons();



    }

    private void initializeButtons(){
        // Find buttons by ID
        Button button1 = findViewById(R.id.calendarButton1);
        Button button2 = findViewById(R.id.calendarButton2);
        Button button3 = findViewById(R.id.calendarButton3);
        Button button4 = findViewById(R.id.calendarButton4);
        Button button5 = findViewById(R.id.calendarButton5);

        // Find textView by ID
        TextView textView1 = findViewById(R.id.textView2);

        // Get day of month of today
        String today = Calendar.getToday();
        String month = Calendar.getCurrentMonth();

        // Set the button texts and the textView text
        button1.setText(Calendar.calculateDay(today, -2));
        button2.setText(Calendar.calculateDay(today, -1));
        button3.setText(today);
        button4.setText(Calendar.calculateDay(today, +1));
        button5.setText(Calendar.calculateDay(today, +2));
        textView1.setText(Calendar.getCurrentMonth());

        // Default on startput:

        // Set button1 as active
        button1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
        button1.setEnabled(false);
        // Set other buttons as inactive
        button2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
        button2.setEnabled(false);
        button3.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.activeElements));
        button3.setEnabled(true);
        button4.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
        button4.setEnabled(false);
        button5.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.elements));
        button5.setEnabled(false);
    }
}