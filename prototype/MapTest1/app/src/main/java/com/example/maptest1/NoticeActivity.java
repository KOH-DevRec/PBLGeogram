package com.example.maptest1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//通知機能に関するアクティビティ
public class NoticeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ListView myListView = (ListView) findViewById(R.id.myListView);

        //データの準備
        ArrayList<User> users = new ArrayList<>();

        int[] icons = {
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher
        };

        String[] names = {
                "11:12",
                "12:53",
                "14:30"
        };

        String[] locs = {
                "県大花子がフォローしました",
                "付近で投稿がありました",
                "県大花子がいいねしました"
        };

        for (int i = 0; i < icons.length; i++){
            User user = new User();
            user.setIcon(BitmapFactory.decodeResource(getResources(), icons[i]));
            user.setName(names[i]);
            user.setLoc(locs[i]);
            users.add(user);
        }
        // Adapter - ArrayAdapter -UserAdapter
        UserAdapter adapter = new UserAdapter(this,0, users);

        // ListViewに表示
        myListView.setAdapter(adapter);

        // Event
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView name = (TextView) view.findViewById(R.id.name);
                Toast.makeText(NoticeActivity.this, Integer.toString(i) + ":" + name.getText().toString() , Toast.LENGTH_SHORT).show();
                name.setText("Checked!!!");
            }
        });



        ImageButton MButton = findViewById(R.id.bmap);
        ImageButton UButton = findViewById(R.id.buser);
        ImageButton SButton = findViewById(R.id.bgralss);
        ImageButton NButton = findViewById(R.id.bbell);

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
    }

    public class UserAdapter extends ArrayAdapter<User> {
        private LayoutInflater layoutInflater;

        public UserAdapter(Context c, int id, ArrayList<User> users) {
            super(c, id, users);
            this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.activity_notice_listitem,parent,false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.loc = (TextView) convertView.findViewById(R.id.loc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            User user = (User) getItem(pos);
            holder.icon.setImageBitmap(user.getIcon());
            holder.name.setText(user.getName());
            holder.loc.setText(user.getLoc());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView loc;
    }

    public class User {
        private Bitmap icon;
        private String name;
        private String loc;

        public Bitmap getIcon() { return icon; }
        public void setIcon(Bitmap icon) { this.icon = icon; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getLoc() { return loc; }
        public void setLoc(String loc) { this.loc = loc; }
    }
}
