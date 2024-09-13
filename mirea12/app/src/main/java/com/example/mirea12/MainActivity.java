package com.example.mirea12;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Вставка данных
        ContentValues values = new ContentValues();
        values.put("column1", "Test Value 1");
        values.put("column2", "Test Value 2");
        Uri uri = getContentResolver().insert(Uri.parse("content://com.example.provider/data"), values);

        // Запрос данных
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.provider/data"), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Здесь вы можете обрабатывать полученные данные
            }
            cursor.close();
        }
    }
}