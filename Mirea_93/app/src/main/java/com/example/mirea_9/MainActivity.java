package com.example.mirea_9;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText fileNameEditText;
    private EditText fileContentEditText;
    private final String folderName = "MyCustomFolder"; // Имя папки где будут храниться файлы

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameEditText = findViewById(R.id.fileNameEditText);
        fileContentEditText = findViewById(R.id.fileContentEditText);
        Button createFileButton = findViewById(R.id.createFileButton);
        Button appendToFileButton = findViewById(R.id.appendToFileButton);
        Button readFileButton = findViewById(R.id.readFileButton);
        Button deleteFileButton = findViewById(R.id.deleteFileButton);

        createFileButton.setOnClickListener(view -> createFile());
        appendToFileButton.setOnClickListener(view -> appendToFile());
        readFileButton.setOnClickListener(view -> readFile());
        deleteFileButton.setOnClickListener(view -> confirmDeletion());
    }

    private File getFilePath(String fileName) {
        File directory = getDir(folderName, MODE_PRIVATE); // Получаем или создаем папку
        return new File(directory, fileName);
    }

    private void createFile() {
        String fileName = fileNameEditText.getText().toString();
        String fileContent = fileContentEditText.getText().toString();

        if (!fileName.isEmpty()) {
            File file = getFilePath(fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(fileContent.getBytes());
                Toast.makeText(this, "Файл создан", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка создания файла", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void appendToFile() {
        String fileName = fileNameEditText.getText().toString();
        String fileContent = fileContentEditText.getText().toString();

        if (!fileName.isEmpty()) {
            File file = getFilePath(fileName);
            try (FileOutputStream fos = new FileOutputStream(file, true)) {
                fos.write(fileContent.getBytes());
                Toast.makeText(this, "Информация добавлена", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка записи в файл", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        String fileName = fileNameEditText.getText().toString();

        if (!fileName.isEmpty()) {
            File file = getFilePath(fileName);
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] bytes = new byte[(int) file.length()];
                    fis.read(bytes);
                    String fileContent = new String(bytes);
                    fileContentEditText.setText(fileContent);
                    Toast.makeText(this, "Файл прочитан", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeletion() {
        String fileName = fileNameEditText.getText().toString();

        if (!fileName.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Удаление файла")
                    .setMessage("Вы действительно хотите удалить этот файл?")
                    .setPositiveButton("Да", (dialogInterface, i) -> deleteCustomFile(fileName))
                    .setNegativeButton("Нет", null)
                    .show();
        } else {
            Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCustomFile(String fileName) {
        File file = getFilePath(fileName);
        if (file.exists()) {
            if (file.delete()) {
                Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ошибка удаления файла", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
        }
    }
}