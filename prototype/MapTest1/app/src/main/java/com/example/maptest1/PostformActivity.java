package com.example.maptest1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//投稿機能に関するアクティビティ
public class PostformActivity extends AppCompatActivity{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nameEditText, captionEditText, tagEditText;

    private LocationData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postform);

        data = (LocationData)getApplication();

        ImageButton MButton = (ImageButton)findViewById(R.id.bmap);
        ImageButton UButton = findViewById(R.id.buser);
        ImageButton SButton = findViewById(R.id.bgralss);
        ImageButton NButton = findViewById(R.id.bbell);
        ImageButton PButton = findViewById(R.id.btoko);

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
                Intent intent = new Intent(getApplication(), SearchlistActivity.class);
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

        PButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post(nameEditText,captionEditText,tagEditText);
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                startActivity(intent);
            }
        });

        nameEditText = (EditText) findViewById(R.id.edit_text1);
        captionEditText = (EditText) findViewById(R.id.edit_text2);
        tagEditText = (EditText) findViewById(R.id.edit_text3);
    }

    public void post(EditText n, EditText c, EditText t) {
        String name = n.getText().toString();
        String caption = c.getText().toString();
        String tag = t.getText().toString();
        Date date = new Date();
        LatLng pos = new LatLng(data.getCurrentLocation().getLatitude(), data.getCurrentLocation().getLongitude());
        GeoPoint geo = new GeoPoint(data.getCurrentLocation().getLatitude(), data.getCurrentLocation().getLongitude());
        final PostData postData = new PostData(name, caption, tag, date, pos);

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("caption", caption);
        data.put("tag", tag);
        data.put("datetime", date);
        data.put("position", geo);
        data.put("good",0);
        data.put("poster", "None");



        db.collection("posts")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("posts", "DocumentSnapshot successfully written!");
                        Log.d("postsName", postData.getName());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("posts", "Error getting documents");
                    }
                });
    }
}
