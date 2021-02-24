package com.cst2335.yue00015;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> messageList = new ArrayList<>();
    private ChatRoomActivity.MyListAdapter myAdapter;
    SQLiteDatabase db;
    private ListView myList;
    private Button send;
    private Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        EditText typed = findViewById(R.id.typeline);
        String textMessage = typed.getText().toString();

        send = findViewById(R.id.sendbtn);
        receive = findViewById(R.id.rcvbtn);
        myList = (ListView) findViewById(R.id.thelistview);

        myAdapter = new MyListAdapter();
        myList.setAdapter(myAdapter);

        loadDataFromDatabase();
        send.setOnClickListener(click ->{
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.TEXT_MESSAGE, textMessage);
            newRowValues.put(MyOpener.SEND_TYPE, 0);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            Message msgSend = new Message(textMessage, true, newId);
            messageList.add(msgSend);
            typed.setText("");
            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
        });

        receive.setOnClickListener(click ->{
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.TEXT_MESSAGE, textMessage);
            newRowValues.put(MyOpener.SEND_TYPE, 1);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Message msgRcv = new Message(textMessage,false, newId);
            messageList.add(msgRcv);
            typed.setText("");

            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
        });

        myList.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.del_title))
                    .setMessage(getString(R.string.del_msg1) + row + "\n"
                            + getString(R.string.del_msg2) + id )
                    .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                        messageList.remove(row);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.no), (click, arg) -> {
                    })
                    .create().show();
            return true;
        });

    }

    private void loadDataFromDatabase()
    {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String[] columns = {MyOpener.TEXT_MESSAGE, MyOpener.SEND_TYPE, MyOpener.COL_ID};
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null,null, null, null, null);

        int textMessageColumnIndex = results.getColumnIndex(MyOpener.TEXT_MESSAGE);
        int isSentIndex = results.getColumnIndex(MyOpener.SEND_TYPE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        while (results.moveToNext()) {
            String msg = results.getString(textMessageColumnIndex);
            int typeInt = results.getInt(isSentIndex);
            Long dataId  = results.getLong(idColIndex);

            boolean msgType;
            if(typeInt == 0) {
                msgType = true;
            } else {
                msgType = false;
            }

            messageList.add(new Message(msg, msgType, dataId));
        }
   }

    protected void printCursor(Cursor c, int version){
//        Message m = messageList.remove();
        System.out.println(db.getVersion());
        System.out.println(c.getColumnCount());
        System.out.println(c.getCount());
    }

    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Message getItem(int position) {
            return messageList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message msg = (Message) getItem(position);
            LayoutInflater inflater = getLayoutInflater();
                if(msg.getIsSend()){
                    View sendView = inflater.inflate(R.layout.row_send_layout, parent, false);
                    TextView thisRowText = sendView.findViewById(R.id.row_item);
                    thisRowText.setText(msg.getMsg());
                    return sendView;
                }
                else{
                    View rcvView = inflater.inflate(R.layout.row_rcv_layout, parent, false);
                    TextView thisRowText = rcvView.findViewById(R.id.row_item);
                    thisRowText.setText(msg.getMsg());
                    return rcvView;
                }
        }

            @Override
            public long getItemId ( int position){
                return messageList.get(position).getId();
            }
    }

}