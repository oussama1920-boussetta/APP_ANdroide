package com.example.myapplicationexamen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, taskUserEditText;
    private RadioGroup taskStatusRadioGroup;
    private Button addTaskButton;
    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialisation des vues
        taskNameEditText = findViewById(R.id.taskName);
        taskUserEditText = findViewById(R.id.taskUser);
        taskStatusRadioGroup = findViewById(R.id.taskStatusRadioGroup);
        addTaskButton = findViewById(R.id.addTaskButton);

        // Initialisation de la base de données
        taskDatabase = Room.databaseBuilder(this, TaskDatabase.class, "task_db")
                .allowMainThreadQueries()
                .build();

        // Gestion du clic sur le bouton
        addTaskButton.setOnClickListener(v -> {
            String taskName = taskNameEditText.getText().toString();
            String taskUser = taskUserEditText.getText().toString();

            // Vérifier si un RadioButton est sélectionné
            int selectedId = taskStatusRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(AddTaskActivity.this, "Veuillez sélectionner un statut pour la tâche.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtenir le RadioButton sélectionné
            RadioButton selectedRadioButton = findViewById(selectedId);
            String taskStatus = selectedRadioButton.getText().toString();

            // Créer et insérer la tâche dans la base de données
            Task task = new Task(taskName, taskStatus, taskUser);
            taskDatabase.taskDao().insert(task);

            // Rediriger vers l'écran principal après l'ajout de la tâche
            startActivity(new Intent(AddTaskActivity.this, HomeActivity.class));
            finish(); // Optionnel, permet de fermer cette activité
        });
    }
}
