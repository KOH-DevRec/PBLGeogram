package com.example.maptest1;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.LatLng;

import java.util.Date;

//投稿(スレッド)のデータ構造
public class PostData {
    private String name;         //スレッド名
    private String tag;          //タグ(型は変更する可能性有)
    private String caption;      //説明文
    private String firebaseKey;  //IDの代用
    public Date timestamp;       //日時
    private LatLng position;     //投稿位置
    public int Good;             //いいね数

    public PostData(){}

    //データ送信用(postformで利用)
    public PostData(String key, String name, String tag, String cap, Date date){
        this.firebaseKey = key;
        this.name = name;
        this.tag = tag;
        this.caption = cap;
        this.timestamp = date;
        //this.position = pos;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getFirebaseKey() { return firebaseKey; }
    public void setFirebaseKey(String firebaseKey) { this.firebaseKey = firebaseKey; }

    @ServerTimestamp
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public int getGood() { return Good; }
    public void setGood(int good) { this.Good = good; }

    public LatLng getPosition() { return position; }
    public void setPosition(LatLng pos) { this.position = pos; }
}
