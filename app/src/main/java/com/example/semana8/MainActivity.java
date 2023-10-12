package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      imageView = findViewById(R.id.imageView);
      button= findViewById(R.id.button);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              new Thread(new Runnable() {
                  @Override
                  public void run() {
                      final Bitmap bitmap= loadImageFromNetwork("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-p5b2NSm9qaWMkdLBzDSx8fOpgdEkuoC7pQ&usqp=CAU");
                      imageView.post(new Runnable() {
                          @Override
                          public void run() {
                              imageView.setImageBitmap(bitmap);
                          }
                      });
                  }
              }).start();
          }
      });
        SensorManager sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }
    private Bitmap loadImageFromNetwork(String url){
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is  = conn.getInputStream();
            return BitmapFactory.decodeStream(is);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}