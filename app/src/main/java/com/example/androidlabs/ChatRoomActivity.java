package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<String> elements = new ArrayList<>();
    private MyListAdapter myAdapter;

    Button send, receive;
    EditText msg;

    boolean msgSendFormat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.list);
        myList.setAdapter( myAdapter = new MyListAdapter());

        send = (Button) findViewById(R.id.send);
        receive = (Button) findViewById(R.id.receive);
        msg = (EditText) findViewById(R.id.typeHere);



        //When user clicks on the receive button
        receive.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                msgSendFormat = false; //Added to know which layout to use

                elements.add(msg.getText().toString());
                msg.getText().clear();
                myAdapter.notifyDataSetChanged();
            }

        });

        //When user clicks on send button
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                msgSendFormat = true;

                elements.add(msg.getText().toString());
                msg.getText().clear();
                myAdapter.notifyDataSetChanged();
            }

        });


        //If you click on a message
        myList.setOnItemLongClickListener( (parent, view, position, id) ->{

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is " + position + "\nThe database id is " + id)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(position);
                        myAdapter.notifyDataSetChanged();
                    })

                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //Show the dialog
                    .create().show();

            return true;

        });

    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return elements.size(); }

        //public Object getItem(int position) { return "This is row " + position; }
        public Object getItem(int position) { return elements.get(position); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if(newView == null) {
                if (msgSendFormat)
                    newView = inflater.inflate(R.layout.message_send, parent, false);
                else
                    newView = inflater.inflate(R.layout.message, parent, false);

            }

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.displayMsg);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;

        }

    }

}
