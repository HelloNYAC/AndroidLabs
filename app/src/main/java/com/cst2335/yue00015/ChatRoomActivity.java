package com.cst2335.yue00015;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> messageList = new ArrayList<>();
    private ChatRoomActivity.MyListAdapter myAdapter;
    SQLiteDatabase db;
    private ListView myList;
    private Button send;
    private Button receive;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";

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
            newRowValues.put(MyOpener.TEXT_MESSAGE, typed.getText().toString());
            newRowValues.put(MyOpener.SEND_TYPE, 0);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            Message msgSend = new Message(typed.getText().toString(), true, newId);
            messageList.add(msgSend);
            typed.setText("");
            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();

        });

        receive.setOnClickListener(click ->{
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.TEXT_MESSAGE, typed.getText().toString());
            newRowValues.put(MyOpener.SEND_TYPE, 1);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Message msgRcv = new Message(typed.getText().toString(),false, newId);
            messageList.add(msgRcv);
            typed.setText("");

            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
        });

        boolean isTablet = findViewById(R.id.fragmentLocation) != null;


        myList.setOnItemClickListener((parent, view, row, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, messageList.get(row).getMsg());
            dataToPass.putLong(ITEM_ID, id);
            dataToPass.putBoolean(ITEM_POSITION, messageList.get(row).getIsSend());

            if(isTablet)
            {
                 //add a DetailFragment
                DetailsFragment dFragment = new DetailsFragment();
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment,Long.toString(id)) //Add the fragment in FrameLayout
                        .addToBackStack(null)
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });

        myList.setOnItemLongClickListener((parent, view, row, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.del_title))
                    .setMessage(getString(R.string.del_msg1) + row + "\n"
                            + getString(R.string.del_msg2) + id)
                    .setPositiveButton(getString(R.string.yes), (click, arg) -> {
                        messageList.remove(row);
                        if(isTablet){
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(getSupportFragmentManager().findFragmentByTag(Long.toString(id)))
                                    .commit();
                        }
                        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?",
                                new String[] {Long.toString(myAdapter.getItemId(row))});
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

        printCursor(results, db.getVersion());

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

    public void printCursor(Cursor c, int version){
        Log.e("Version", Integer.toString(db.getVersion()));
        Log.e("Count of Columns", Integer.toString(c.getColumnCount()));
        String[] columnNames = c.getColumnNames();
        Log.e("Column Names", Arrays.deepToString(columnNames));
        Log.e("Count of Row", Integer.toString(c.getCount()));
        c.moveToFirst();
        for(int i=1; i < c.getCount(); i++){
            Log.e("row", Integer.toString(i)
                    + ";  message  " + c.getString(c.getColumnIndex(MyOpener.TEXT_MESSAGE))
                    + ";  isSend:  " + Integer.toString(c.getInt(c.getColumnIndex(MyOpener.SEND_TYPE)))
                    + ";  _id: " + Integer.toString(c.getInt(c.getColumnIndex(MyOpener.COL_ID))));
            c.moveToNext();
        }
            c.moveToPosition(-1);
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