package com.thomas.trainingplanner.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thomas.trainingplanner.R;
import com.thomas.trainingplanner.database.AppDatabase;
import com.thomas.trainingplanner.database.Exercise;
import com.thomas.trainingplanner.database.ExerciseCompleted;
import com.thomas.trainingplanner.database.TrainingDay;
import com.thomas.trainingplanner.entities.ExerciseData;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<ExerciseData> exercises = new ArrayList<>();
    private Context context;

    public ExerciseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseData exercise = exercises.get(position);
        holder.nameText.setText(exercise.getName());
        holder.detailsText.setText(String.format("Weight: %.1f kg | Reps: %d | RPE: %.1f",
                exercise.getWeight(), exercise.getReps(), exercise.getRpe()));

        holder.deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(position));
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Exercise")
                .setMessage("Are you sure you want to delete this exercise?")
                .setPositiveButton("Yes", (dialog, which) -> deleteExercise(position))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteExercise(int position) {
        if (position < 0 || position >= exercises.size()) {
            return; // Guard against invalid positions
        }

        ExerciseData exerciseToDelete = exercises.get(position);

        new Thread(() -> {
            try {
                AppDatabase db = AppDatabase.getInstance(context);

                Exercise exercise = db.exerciseDao().getExerciseByName(exerciseToDelete.getName());
                if (exercise != null) {
                    // First delete related records
                    List<ExerciseCompleted> completedExercises =
                            db.exerciseCompletedDao().getCompletedExercises(exercise.getId());
                    for (ExerciseCompleted completed : completedExercises) {
                        db.exerciseCompletedDao().delete(completed);
                    }

                    // Delete training days for this exercise
                    List<TrainingDay> trainingDays =
                            db.trainingDayDao().getTrainingDaysByExerciseId(exercise.getId());
                    for (TrainingDay day : trainingDays) {
                        db.trainingDayDao().delete(day);
                    }

                    // Finally delete the exercise
                    db.exerciseDao().delete(exercise);

                    ((Activity) context).runOnUiThread(() -> {
                        // Safely remove the item
                        if (position < exercises.size()) {
                            exercises.remove(position);
                            notifyItemRemoved(position);
                            // Notify that the data set changed to refresh the view
                            notifyDataSetChanged();
                        }
                        Toast.makeText(context, "Exercise deleted", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Error deleting exercise: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<ExerciseData> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView detailsText;
        ImageButton deleteButton;

        ExerciseViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.exerciseNameText);
            detailsText = itemView.findViewById(R.id.exerciseDetailsText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}