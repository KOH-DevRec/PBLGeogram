package com.example.maptest1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

//ホームマップ上での機能に関するアクティビティ
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private LocationData data;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<PostData> pData;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        data = (LocationData)getApplication();
        pData = new ArrayList<>();

        ImageButton MButton = findViewById(R.id.bmap);
        ImageButton UButton = findViewById(R.id.buser);
        ImageButton SButton = findViewById(R.id.bgralss);
        ImageButton NButton = findViewById(R.id.bbell);

        ImageButton PButton = findViewById(R.id.bpost);

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
                Intent intent = new Intent(getApplication(), PostformActivity.class);
                startActivity(intent);
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
                                LatLng geoLatLng = new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude());

                                pData.add(new PostData(document.getId(),
                                        document.getString("name"),
                                        document.getString("tag"),
                                        document.getString("caption"),
                                        timeStamp,
                                        geoLatLng));

                                Log.d("postsName", pData.get(i).getName());
                                mMap.addMarker(new MarkerOptions().position(pData.get(i).getPosition()).title(pData.get(i).getName()).snippet(pData.get(i).getCaption())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                i++;
                            }
                        } else {
                            Log.d("posts", "Error getting documents");
                        }
                    }
                });
        /*
        for(i=0; i > pData.size(); i++){
            mMap.addMarker(new MarkerOptions().position(pData.get(i).getPosition()).title(pData.get(i).getName()).snippet(pData.get(i).getCaption()));
            Log.d("postsxx", pData.get(i).getName());
         }*/
        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // 現在地用
        //LatLng current = new LatLng(data.getCurrentLocation().getLatitude(), data.getCurrentLocation().getLongitude());
        //定位置用
        LatLng IPU = new LatLng(39.802642, 141.137537);

        mMap.addMarker(new MarkerOptions().position(IPU).title("現在地").snippet("投稿位置になります"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IPU, 15));
    }
}