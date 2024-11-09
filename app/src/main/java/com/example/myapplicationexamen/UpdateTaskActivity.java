package com.example.myapplicationexamen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateTaskActivity extends AppCompatActivity {
    private EditText taskNameEditText;
    private EditText taskUserEditText;
    private Spinner taskStatusSpinner;
    private View updateButton;

    private TaskDatabase taskDatabase;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // Récupération des vues
        taskNameEditText = findViewById(R.id.taskNameEditText);
        taskUserEditText = findViewById(R.id.taskUserEditText);
        taskStatusSpinner = findViewById(R.id.taskStatusSpinner);
        updateButton = findViewById(R.id.updateButton);

        // Initialiser la base de données
        taskDatabase = TaskDatabase.getDatabase(this);

        // Récupérer l'ID de la tâche passée en paramètre
        int taskId = getIntent().getIntExtra("taskId", -1);
        if (taskId != -1) {
            task = taskDatabase.taskDao().getTaskById(taskId);
            if (task != null) {
                displayTaskDetails(task);
            }
        }

        // Configurer le Spinner pour les statuts de la tâche
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusSpinner.setAdapter(adapter);

        // Mettre à jour la tâche lors du clic sur le bouton "Update"
        updateButton.setOnClickListener(v -> {
            updateTask();
            finish();  // Fermer l'activity et revenir à HomeActivity
        });
    }

    // Afficher les détails de la tâche
    private void displayTaskDetails(Task task) {
        taskNameEditText.setText(task.getName());
        taskUserEditText.setText(task.getUser());
        String[] statuses = getResources().getStringArray(R.array.task_status_array);
        int statusPosition = java.util.Arrays.asList(statuses).indexOf(task.getStatus());
        taskStatusSpinner.setSelection(statusPosition);
    }

    // Mettre à jour les informations de la tâche
    private void updateTask() {
        String updatedStatus = taskStatusSpinner.getSelectedItem().toString();
        task.setStatus(updatedStatus);
        taskDatabase.taskDao().update(task);
    }
}
