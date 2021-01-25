package com.example.maptest1;
import android.app.Application;
import android.location.Location;

//現在地情報のgetter&setterに関するクラス
public class LocationData extends Application{
    private Location currentLocation;

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public void setCurrentLocation(Location location){
        this.currentLocation = location;
    }
}
