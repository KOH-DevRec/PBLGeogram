package com.example.maptest1;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;

//投稿機能に関するアクティビティ
public class PostformActivity extends AppCompatActivity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    EditText nameEditText, captionEditText, tagEditText;
    int RESULT_PICK_FILENAME = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postform);

        ImageButton MButton = (ImageButton)findViewById(R.id.bmap);
        ImageButton UButton = findViewById(R.id.buser);
        ImageButton SButton = findViewById(R.id.bgralss);
        ImageButton NButton = findViewById(R.id.bbell);
        ImageButton PButton = findViewById(R.id.btoko);
        ImageButton GButton = findViewById(R.id.bphoto);

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
//ギャラリーからファイルを選ぶ
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bphoto:
            pickFilenameFromGallery();
            break;
        }
    }

    private void pickFilenameFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,RESULT_PICK_FILENAME);
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_PICK_FILENAME
        && resultCode == RESULT_OK
        && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { Media.DATA };

            Cursor cursor = getContentResolver().query(
                    selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int ColumnIndex
                    = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Toast.makeText(
                    this,
                    picturePath,
                    Toast.LENGTH_LONG).show()
;
        }
    }

    public void post(View v) {
        String name = nameEditText.getText().toString();
        String caption = captionEditText.getText().toString();
        String tag = tagEditText.getText().toString();
        Date date = new Date();
        String key = reference.push().getKey();

//    引数のToDoDataの内容をデータベースに送る。
        PostData postData = new PostData(key, name, caption, tag, date);

        reference.child(key).setValue(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                finish();
            }
        });
    }
}
