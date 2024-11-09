package com.example.myapplicationexamen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


            sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }

            usernameEditText = findViewById(R.id.username);
            passwordEditText = findViewById(R.id.password);
            loginButton = findViewById(R.id.loginButton);

            loginButton.setOnClickListener(v -> {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.equals("admin") && password.equals("1234")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
