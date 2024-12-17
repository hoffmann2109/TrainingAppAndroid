package com.thomas.trainingplanner.customElements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;// CustomToast.java

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.thomas.trainingplanner.R;

public class CustomToast {
    private static final int CUSTOM_BACKGROUND = R.drawable.toast_background;
    private static final int TEXT_COLOR = R.color.background;
    private static final int CORNER_RADIUS = 25;
    private static final int PADDING = 24;

    @SuppressLint("ResourceAsColor")
    public static void showCustomToast(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);

        // Get the Toast's view
        View view = toast.getView();

        // Create a new background drawable
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(ContextCompat.getColor(context, R.color.elements));
        shape.setCornerRadius(CORNER_RADIUS);

        // Apply styling to the view
        view.setBackground(shape);
        view.setPadding(PADDING, PADDING, PADDING, PADDING);

        // Style the text
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(TEXT_COLOR);
        text.setGravity(Gravity.CENTER);

        // Show the toast
        toast.show();
    }
}
