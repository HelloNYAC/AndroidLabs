package com.cst2335.yue00015;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>(Arrays.asList());
//    private MyListAdapter myAdapter;
    private ListView myList;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        chat = findViewById(R.id.chatbutton);
        Intent GoToChat =new Intent( ProfileActivity.this, ChatRoomActivity.class);
        chat.setOnClickListener(click -> {
            startActivity(GoToChat);
        });

//        myList = findViewById(R.id.list_item);
//        myList.setAdapter(myAdapter);
        }

//        class MyListAdapter extends BaseAdapter {
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return list.get(position);
//            }
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//
//        }

    }
