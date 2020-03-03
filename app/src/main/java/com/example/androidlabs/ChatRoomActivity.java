package com.example.androidlabs;

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

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> elements = new ArrayList<>();
    private MyListAdapter myAdapter;

    MyOpener myOpener;


    Button send, receive;
    EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.list);
        myList.setAdapter(myAdapter = new MyListAdapter());

        send = (Button) findViewById(R.id.send);
        receive = (Button) findViewById(R.id.receive);
        msg = (EditText) findViewById(R.id.typeHere);

        myOpener = new MyOpener(this);


        //When user clicks on the receive button
        receive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String message = msg.getText().toString();

                long newID = myOpener.insertMessage(message, 1);

                elements.add(new Message(message, 1, newID));
                msg.getText().clear();
                myAdapter.notifyDataSetChanged();
            }

        });

        //When user clicks on send button
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String message = msg.getText().toString();

                long newID = myOpener.insertMessage(message, 0);

                elements.add(new Message(message, 0, newID));
                //elements.add(new Message(msg.getText().toString(), 0, newID));
                msg.getText().clear();
                myAdapter.notifyDataSetChanged();
            }

        });


        //If you click on a message
        myList.setOnItemLongClickListener((parent, view, position, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is " + position + "\nThe database id is " + id)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(position);

                        myOpener.removeRow(position);

                        myAdapter.notifyDataSetChanged();
                    })

                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> {
                    })

                    //Show the dialog
                    .create().show();

            return true;

        });

        myOpener.printCursor();

    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }

        //public Object getItem(int position) { return "This is row " + position; }
        public Message getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {

            View newView;
            LayoutInflater inflater = getLayoutInflater();

            if (elements.get(position).getType() == 1) {
                newView = inflater.inflate(R.layout.message_send, parent, false);
            }
            else {
                newView = inflater.inflate(R.layout.message, parent, false);
            }
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.displayMsg);
            //tView.setText(getItem(position).toString());
            tView.setText(elements.get(position).getMsg());

            //return it to be put in the table
            return newView;

        }

    }

    //Message class
    public static class Message {
        private String msg;
        private int type; // 0 is send 1 is receive
        private long id;

        //Constructor
        public Message(String msg, int type, long id) {
            this.msg = msg;
            this.type = type;
            this.id = id;
        }

        //Get methods
        public String getMsg() {
            return msg;
        }
        public int getType() {
            return type;
        }
        public long getID() { return id; }

        //Set methods
        public void setMsg(String msg) {
            this.msg = msg;
        }
        public void setType(int type) {
            this.type = type;
        }
        public void setID(long id) { this.id = id; }

    }


}
