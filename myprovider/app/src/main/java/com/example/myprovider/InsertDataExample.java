package com.example.myprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class InsertDataExample {

    public static void insertData(Context context, String name, int age, String email) {
        // Создаем объект ContentValues для хранения данных
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("email", email);

        // Создаем ContentResolver для доступа к вашему ContentProvider
        ContentResolver contentResolver = context.getContentResolver();

        // Определяем URI для вставки данных
        Uri uri = Uri.parse("content://com.example.myprovider/users");

        // Выполняем запрос на вставку данных
        Uri insertedUri = contentResolver.insert(uri, values);

        // Проверяем, была ли успешно вставлена запись
        if (insertedUri != null) {
            // Если успешно, выводим сообщение об успешной вставке
            Log.d("Insert", "Data inserted successfully at: " + insertedUri);
        } else {
            // Если не удалось вставить запись, выводим сообщение об ошибке
            Log.d("Insert", "Failed to insert data");
        }
    }

    public static void main(String[] args) {
        // Пример использования
        Context context = null; // Подставьте ваш контекст приложения
        insertData(context, "John Doe", 30, "john@example.com");
    }
}
