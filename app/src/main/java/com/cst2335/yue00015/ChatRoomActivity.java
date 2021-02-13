package com.cst2335.yue00015;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private ChatRoomActivity.MyListAdapter myAdapter;
    private ListView myList;
    private Button send;
    private Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        myAdapter = new MyListAdapter();
        myList = (ListView)findViewById(R.id.thelistview);
        myList.setAdapter(myAdapter);

        EditText typed = findViewById(R.id.typeline);

        send = findViewById(R.id.sendbtn);
        send.setOnClickListener(click ->{
            String sendText = typed.getText().toString();
            list.add(sendText);
            myAdapter.notifyDataSetChanged();
        });

//        receive = findViewById(R.id.rcvbtn);
//        receive.setOnClickListener(click ->{
//            list.add("Hiii");
//            myAdapter.notifyDataSetChanged();
//        });
    }

//    class Message
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View thisRow = inflater.inflate(R.layout.row_send_layout, parent, false);
            TextView thisRowText = thisRow.findViewById(R.id.row_item);
            thisRowText.setText(getItem(position).toString());
            return thisRow;
            }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }
    }
}