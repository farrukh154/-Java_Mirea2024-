package com.example.mirea_8;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText fileNameEditText, fileContentEditText;
    private Button createFileButton, appendToFileButton, readFileButton, deleteFileButton;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем UI компоненты
        fileNameEditText = findViewById(R.id.file_name_edit_text);
        fileContentEditText = findViewById(R.id.file_content_edit_text);
        createFileButton = findViewById(R.id.create_file_button);
        appendToFileButton = findViewById(R.id.append_to_file_button);
        readFileButton = findViewById(R.id.read_file_button);
        deleteFileButton = findViewById(R.id.delete_file_button);

        // Установка обработчиков нажатий
        createFileButton.setOnClickListener(v -> createFile());
        appendToFileButton.setOnClickListener(v -> appendToFile());
        readFileButton.setOnClickListener(v -> readFile());
        deleteFileButton.setOnClickListener(v -> confirmFileDeletion());

        // Восстанавливаем состояние после поворота экрана
        if (savedInstanceState != null) {
            fileNameEditText.setText(savedInstanceState.getString("fileName"));
            fileContentEditText.setText(savedInstanceState.getString("fileContent"));
        }

        // Проверка разрешений
        checkPermission();
    }

    private void createFile() {
        String fileName = fileNameEditText.getText().toString().trim();
        String fileContent = fileContentEditText.getText().toString();
        File file = new File(getExternalFilesDir(null), fileName);

        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(fileContent.getBytes());
            Toast.makeText(this, "Файл создан", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка при создании файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void appendToFile() {
        String fileName = fileNameEditText.getText().toString().trim();
        String fileContent = fileContentEditText.getText().toString();
        File file = new File(getExternalFilesDir(null), fileName);

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write((fileContent + "\n").getBytes());
            Toast.makeText(this, "Информация добавлена в файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка при записи в файл", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        String fileName = fileNameEditText.getText().toString().trim();
        File file = new File(getExternalFilesDir(null), fileName);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            fileContentEditText.setText(new String(bytes));
            Toast.makeText(this, "Файл прочитан", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка при чтении файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmFileDeletion() {
        String fileName = fileNameEditText.getText().toString().trim();
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение удаления")
                .setMessage("Вы действительно хотите удалить файл \"" + fileName + "\"?")
                .setPositiveButton("Удалить", (dialog, which) -> deleteFile())
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void deleteFile() {
        String fileName = fileNameEditText.getText().toString().trim();
        File file = new File(getExternalFilesDir(null), fileName);
        if (file.delete()) {
            fileNameEditText.setText("");
            fileContentEditText.setText("");
            Toast.makeText(this, "Файл удалён", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка при удалении файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if (!writeAccepted || !readAccepted) {
                Toast.makeText(this, "Необходимо разрешение для работы с файлами", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fileName", fileNameEditText.getText().toString());
        outState.putString("fileContent", fileContentEditText.getText().toString());
    }
}