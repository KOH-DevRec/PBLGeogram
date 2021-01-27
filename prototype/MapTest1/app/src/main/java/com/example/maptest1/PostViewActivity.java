package com.example.maptest1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class PostViewActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<PostData> pData;
    private int i=0;
    EditText commentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postview);

        pData = new ArrayList<>();

        ImageButton MButton = findViewById(R.id.bmap);
        ImageButton UButton = findViewById(R.id.buser);
        ImageButton SButton = findViewById(R.id.bgralss);
        ImageButton NButton = findViewById(R.id.bbell);
        ImageButton GButton = findViewById(R.id.bgood);
        ImageButton CButton = findViewById(R.id.mycomment);

        MButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                startActivity(intent);
            }
        });

        UButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), UserActivity.class);
                startActivity(intent);
            }
        });

        SButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PostViewActivity.class);
                startActivity(intent);
            }
        });

        NButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        GButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView good = (TextView) findViewById(R.id.text7);
                TextView goodnum = (TextView) findViewById(R.id.text8);

                if(goodnum.getText().toString() =="               113"){
                    good.setTextColor(Color.BLACK);
                    goodnum.setTextColor(Color.BLACK);
                    goodnum.setText("               112");
                }else{
                    good.setTextColor(Color.CYAN);
                    goodnum.setTextColor(Color.CYAN);
                    goodnum.setText("               113");
                }
            }
        });

        CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentEditText = (EditText) findViewById(R.id.ctext);
                String c = commentEditText.getText().toString();
                TextView cc = (TextView) findViewById(R.id.edit_text4);

                cc.setText(c);
            }
        });

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("posts", document.getId() + " => " + document.getData());

                                Date timeStamp = document.getTimestamp("datetime").toDate();
                                GeoPoint geoPoint = document.getGeoPoint("position");
                                LatLng geoLatLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

                                pData.add(new PostData(document.getString("name"),
                                        document.getString("tag"),
                                        document.getString("caption"),
                                        timeStamp,
                                        geoLatLng));

                                Log.d("postsName", pData.get(i).getName());
                                i++;
                            }
                        } else {
                            Log.d("posts", "Error getting documents");
                        }
                    }
                });

    }

    public void textset(PostData p){
        TextView name = findViewById(R.id.text);
    }
}
