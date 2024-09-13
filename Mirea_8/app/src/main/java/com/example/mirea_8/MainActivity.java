package com.example.mirea_8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация компонентов пользовательского интерфейса
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.buttonLoadImage);

        button.setOnClickListener(v -> loadImage());

        // Запускаем две задачи параллельно
        executor.submit(this::task1);
        executor.submit(this::task2);
        executor.submit(this::task3);
    }

    private void loadImage() {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    URL url = new URL("https://random.dog/woof.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder jsonResult = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResult.append(line);
                    }

                    JSONObject jsonObject = new JSONObject(jsonResult.toString());
                    String imageUrl = jsonObject.getString("url");

                    InputStream imageStream = new URL(imageUrl).openStream();
                    return BitmapFactory.decodeStream(imageStream);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }.execute();
    }

    private void task1() {
        // Имитация длительной операции
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task2() {
        // Имитация другой длительной операции
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task3() {
        // Имитация третьей длительной операции
        try {
            Thread.sleep(4000); // Задача на 4 секунды, к примеру
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}