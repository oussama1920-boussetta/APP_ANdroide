package com.example.myapplicationexamen;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface taskDAO {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :id")
    Task getTaskById(int id);
}
