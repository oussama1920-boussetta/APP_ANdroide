package com.example.myapplicationexamen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur
    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    // Interface pour gérer les clics sur les éléments
    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    // Définir le listener de clics
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Créer de nouveaux ViewHolders (une seule fois par élément visible)
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    // Lier les données de chaque tâche à l'élément UI du ViewHolder
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.taskUser.setText(task.getUser());

        // Afficher l'icône correspondant au statut de la tâche
        int statusIcon;
        switch (task.getStatus()) {
            case "À faire":
                statusIcon = R.drawable.ic_doing;
                break;
            case "En cours":
                statusIcon = R.drawable.ic_doing;
                break;
            case "Terminé":
                statusIcon = R.drawable.ic_done;
                break;
            default:
                statusIcon = R.drawable.ic_todo; // Icône par défaut
                break;
        }
        holder.taskStatusImage.setImageResource(statusIcon);

        // Définir le clic sur l'élément
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(task);
            }
        });
    }

    // Retourner la taille de la liste de tâches
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ViewHolder pour lier les vues de task_item.xml
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName, taskUser;
        private ImageView taskStatusImage;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            taskUser = itemView.findViewById(R.id.taskUser);
            taskStatusImage = itemView.findViewById(R.id.taskStatusImage);
        }
    }
}
