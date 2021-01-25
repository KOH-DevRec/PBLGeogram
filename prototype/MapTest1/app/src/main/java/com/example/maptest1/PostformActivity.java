package com.example.maptest1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.time.LocalDateTime;
import java.util.Date;

//投稿機能に関するアクティビティ
public class PostformActivity extends AppCompatActivity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
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
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                startActivity(intent);
            }
        });

        nameEditText = (EditText) findViewById(R.id.edit_text1);
        captionEditText = (EditText) findViewById(R.id.edit_text2);
        tagEditText = (EditText) findViewById(R.id.edit_text3);
    }

    public void post(View v) {
        String name = nameEditText.getText().toString();
        String caption = captionEditText.getText().toString();
        String tag = tagEditText.getText().toString();
        Date date = new Date();
        LatLng pos = new LatLng(data.getCurrentLocation().getLatitude(), data.getCurrentLocation().getLongitude());
        String key = reference.push().getKey();
        PostData postData = new PostData(key, name, caption, tag, date, pos);

        reference.child(key).setValue(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                finish();
            }
        });
    }
}
