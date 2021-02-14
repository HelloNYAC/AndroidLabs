package com.cst2335.yue00015;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<>();
    private ChatRoomActivity.MyListAdapter myAdapter;
    private ListView myList;
    private Button send;
    private Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        myAdapter = new ChatRoomActivity.MyListAdapter();
        myList = (ListView) findViewById(R.id.thelistview);
        myList.setAdapter(myAdapter);

        myList.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.del_title))
                    .setMessage(getResources().getString(R.string.del_msg1) + row + "\n"
                            + getResources().getString(R.string.del_msg2) + id )
                    .setPositiveButton("Yes", (click, arg) -> {
                        list.remove(row);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });



        EditText typed = findViewById(R.id.typeline);

        send = findViewById(R.id.sendbtn);
        send.setOnClickListener(click ->{
            Message msgSend = new Message(typed.getText().toString(), R.drawable.row_send, 0);
            list.add(msgSend);
            myAdapter.notifyDataSetChanged();
        });

        receive = findViewById(R.id.rcvbtn);
        receive.setOnClickListener(click ->{
            Message msgRcv = new Message(typed.getText().toString(), R.drawable.row_receive, 1);
            list.add(msgRcv);
            myAdapter.notifyDataSetChanged();
        });
    }

    class Message{
        private String msg;
        private int layout;
        private int sourceImg;

        public Message (String msg, int sourceImg, int layout){
            this.msg = msg;
            this.sourceImg = sourceImg;
            this.layout = layout;
        }

        public int getLayout(){
        return layout;
        }

        public int getSourceImg(){
        return sourceImg;
        }

        public String getMsg() {
            return msg;
        }
    }

    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override      public View getView(int position, View convertView, ViewGroup parent) {
            Message msg = (Message) getItem(position);
            int msgLayout = msg.getLayout();
            LayoutInflater inflater = getLayoutInflater();

            switch (msgLayout) {
                case 0: {
                    View sendView = inflater.inflate(R.layout.row_send_layout, parent, false);
                    TextView thisRowText = sendView.findViewById(R.id.row_item);
                    ImageView thisRowImage = sendView.findViewById(R.id.row_title);
                    thisRowText.setText(msg.getMsg());
                    thisRowImage.setImageResource(R.drawable.row_send);
                    return sendView;
                }
                case 1: {
                    View rcvView = inflater.inflate(R.layout.row_rcv_layout, parent, false);
                    TextView thisRowText = rcvView.findViewById(R.id.row_item);
                    ImageView thisRowImage = rcvView.findViewById(R.id.row_title);
                    thisRowText.setText(msg.getMsg());
                    thisRowImage.setImageResource(R.drawable.row_receive);
                    return rcvView;
                }
                default:
                    return null;
            }
        }

            @Override
            public long getItemId ( int position){
                return (long) position;
            }
    }

}