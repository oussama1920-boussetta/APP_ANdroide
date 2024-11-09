package com.example.myapplicationexamen;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


    @Entity(tableName = "task_table")
    public class Task {
        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "name")
        private String name;

        @ColumnInfo(name = "status")
        private String status;

        @ColumnInfo(name = "user")
        private String user;

        // Constructeur
        public Task(String name, String status, String user) {
            this.name = name;
            this.status = status;
            this.user = user;
        }

        // Getters et setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
    }


