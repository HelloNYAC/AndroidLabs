package com.cst2335.yue00015;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_details, container, false);

        TextView message = result.findViewById(R.id.frag_msg);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        TextView idView = result.findViewById(R.id.frag_id);
        idView.setText("ID = " + id);

        CheckBox isSent = result.findViewById(R.id.frag_ckbx);
        isSent.setChecked(dataFromActivity.getBoolean(ChatRoomActivity.ITEM_POSITION));

        Button hideButton = result.findViewById(R.id.frag_hdbtn);
        hideButton.setOnClickListener( click -> {
            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return result;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}