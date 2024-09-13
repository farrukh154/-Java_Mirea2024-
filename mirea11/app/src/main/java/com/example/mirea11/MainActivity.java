package com.example.mirea11;
/*Реализовать в приложении просмотр любой веб-страницы через
WebView*/
/*
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.ru/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
*/


/*Реализация проигрывания музыки*/

/*
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button playButton = findViewById(R.id.playButton);
        mediaPlayer = new MediaPlayer();
        try {
            // Убедитесь, что URL заменен на реальный URL аудиофайла
            mediaPlayer.setDataSource("https://www2.cs.uic.edu/~i101/SoundFiles/BabyElephantWalk60.wav");
            // Для сетевых потоков используйте prepareAsync() и слушателя OnPreparedListener
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // После подготовки MediaPlayer начните воспроизведение
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mp.isPlaying()) {
                            mp.start();
                        } else {
                            mp.pause();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
*/

/*Реализовать различные анимации элементов UI*/

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScale;
    private TextView textViewTranslate;
    private TextView textViewRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3); // Убедитесь, что название файла layout правильное

        // Инициализируйте TextView для каждой анимации
        textViewScale = findViewById(R.id.textViewScale);
        textViewTranslate = findViewById(R.id.textViewTranslate);
        textViewRotate = findViewById(R.id.textViewRotate);

        // Настройте анимацию увеличения
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        textViewScale.startAnimation(scaleAnimation);

        // Настройте анимацию перемещения
        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        textViewTranslate.startAnimation(translateAnimation);

        // Настройте анимацию вращения
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        textViewRotate.startAnimation(rotateAnimation);
    }
}

