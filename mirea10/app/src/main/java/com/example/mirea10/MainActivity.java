package com.example.mirea10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesManager sharedPreferencesManager;
    private EditText editTextUsername;
    private Button buttonSave, buttonShow, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferencesManager = new SharedPreferencesManager(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSave = findViewById(R.id.buttonSave);
        buttonShow = findViewById(R.id.buttonShow);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonSave.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            sharedPreferencesManager.saveUsername(username);
        });

        buttonShow.setOnClickListener(v -> {
            editTextUsername.setText(sharedPreferencesManager.getUsername());
        });

        buttonDelete.setOnClickListener(v -> {
            sharedPreferencesManager.removeUsername();
            editTextUsername.setText("");
        });
    }
}