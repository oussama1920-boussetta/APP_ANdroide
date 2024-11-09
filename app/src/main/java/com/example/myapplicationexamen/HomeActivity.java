package com.example.myapplicationexamen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDatabase taskDatabase;
    private FloatingActionButton newTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newTaskButton = findViewById(R.id.newTaskButton);

        // Initialisation de la base de données
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task_db")
                .fallbackToDestructiveMigration()
                .build();

        // Charger les tâches et mettre à jour l'affichage
        loadTasks();

        // Ouvrir AddTaskActivity lors du clic sur le bouton flottant
        newTaskButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AddTaskActivity.class));
        });
    }

    private void loadTasks() {
        // Charger les tâches dans un thread secondaire pour éviter de bloquer le thread principal
        new Thread(() -> {
            List<Task> taskList = taskDatabase.taskDao().getAllTasks();

            runOnUiThread(() -> {
                // Configuration de l'adaptateur pour le RecyclerView
                taskAdapter = new TaskAdapter(taskList);
                recyclerView.setAdapter(taskAdapter);

                // Gérer les clics sur les éléments du RecyclerView
                taskAdapter.setOnItemClickListener(task -> {
                    Intent intent = new Intent(HomeActivity.this, UpdateTaskActivity.class);
                    intent.putExtra("taskId", task.getId());
                    startActivity(intent);
                });
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharger la liste des tâches lorsqu'on revient à l'activité Home
        loadTasks();
    }
}
