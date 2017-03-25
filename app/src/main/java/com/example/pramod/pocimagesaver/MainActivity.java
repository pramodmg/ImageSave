package com.example.pramod.pocimagesaver;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    String URL = "http://assets.myntassets.com/w_480,q_90/assets/images/1410489/2016/7/6/11467807016246-AKS-Red-Printed-Anarkali-Kurta-1501467807016047-3.jpg";
    ProgressDialog mProgress;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn1);
        img = (ImageView) findViewById(R.id.image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Button clicked", Toast.LENGTH_LONG).show();
                new DownloadImage().execute(URL);
            }
        });
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgress = new ProgressDialog(MainActivity.this);
            mProgress.setTitle("Downloaing Image");
            mProgress.setMessage("Downloading .... ");
            mProgress.setIndeterminate(false);
            mProgress.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageURL = strings[0];
            Bitmap bitmap = null;

            try {
                InputStream inp = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(inp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    "Bird",
                    "Image of bird"
            );
            img.setImageBitmap(bitmap);
            mProgress.hide();
        }
    }
}
