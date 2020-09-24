package com.example.digits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.fingerpaintview.FingerPaintView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {




    FingerPaintView drawndigit;
    TextView prediction;
    TextView probability;
    TextView TimeCost;

    private Classifier mClassifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mClassifier = new Classifier(this);
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();

        }


        drawndigit = findViewById(R.id.digit);
        prediction = findViewById(R.id.prediction);
        probability = findViewById(R.id.probability);
        TimeCost = findViewById(R.id.timecost);

        Button detect = findViewById(R.id.detect);
        Button clear = findViewById(R.id.clear);

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = drawndigit.exportToBitmap(Classifier.IMG_WIDTH,Classifier.IMG_HEIGHT);
                Result res = mClassifier.classify(bitmap);
                probability.setText("Probability: "+res.getProbability()+"");
                prediction.setText("Prediction: "+res.getNumber()+"");
                TimeCost.setText("TimeCost: "+res.getTimeCost()+"");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawndigit.clear();
                prediction.setText("");
                probability.setText("");
                TimeCost.setText("");

            }
        });




    }




}
