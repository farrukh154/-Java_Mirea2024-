package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextField1, editTextField2;
    private TextView textViewResult;
    private Button buttonReadJSON, buttonCreateJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextField1 = findViewById(R.id.editTextField1);
        editTextField2 = findViewById(R.id.editTextField2);
        textViewResult = findViewById(R.id.textViewResult);
        buttonReadJSON = findViewById(R.id.buttonReadJSON);
        buttonCreateJSON = findViewById(R.id.buttonCreateJSON);

        // Создание файла data.json, если его еще нет
        createDataFileIfNotExists();

        buttonCreateJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndSaveJSON();
            }
        });

        buttonReadJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readJSONAndDisplay();
            }
        });
    }

    private void createDataFileIfNotExists() {
        try {
            File file = new File(getFilesDir(), "data.json");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAndSaveJSON() {
        String field1 = editTextField1.getText().toString();
        int field2 = Integer.parseInt(editTextField2.getText().toString());

        MyData newData = new MyData(field1, field2);

        Gson gson = new Gson();
        String json = gson.toJson(newData);

        try {
            File file = new File(getFilesDir(), "data.json");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);
            writer.close();
            Toast.makeText(this, "JSON updated and saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error updating JSON", Toast.LENGTH_SHORT).show();
        }
    }

    private void readJSONAndDisplay() {
        try {
            File file = new File(getFilesDir(), "data.json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            String json = jsonBuilder.toString();
            Gson gson = new Gson();
            MyData data = gson.fromJson(json, MyData.class);

            if (data != null) {
                String result = "Field 1: " + data.getField1() + "\n"
                        + "Field 2: " + data.getField2();
                textViewResult.setText(result);
                textViewResult.setVisibility(View.VISIBLE); // Сделать TextView видимым
            } else {
                textViewResult.setText("Failed to parse JSON");
                textViewResult.setVisibility(View.VISIBLE); // Сделать TextView видимым
            }

        } catch (IOException e) {
            e.printStackTrace();
            textViewResult.setText("Error reading JSON");
            textViewResult.setVisibility(View.VISIBLE); // Сделать TextView видимым
        }
    }
}

