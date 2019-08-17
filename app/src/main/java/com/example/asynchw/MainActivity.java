package com.example.asynchw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {
    URL imageUrl = null;
    InputStream inputStream = null;
    Bitmap bitmap = null;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void downloadImage(View view) {
        CustomAsyncTask customAsyncTask = new CustomAsyncTask();
        customAsyncTask.execute("https://api.androidhive.info/json/movies/2.jpg");

    }
    private class CustomAsyncTask extends AsyncTask<String, String, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                imageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                inputStream = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap = BitmapFactory.decodeStream(inputStream  , null, options);
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(bitmap);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

    }
}
