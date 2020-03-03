package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ContactsDB";
    protected final static int VERSION_NUM = 1;

    public final static String TABLE_NAME = "Texting";

    public final static String COL_MESSAGE = "MESSAGE";
    public final static String COL_TYPE = "TYPE";

    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGE + " text,"
                + COL_TYPE + " integer);" );  // add or remove columns
    }

    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    //Add data to the table
    public long insertMessage(String msg, int type) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_MESSAGE, msg);
        contentValues.put(COL_TYPE, type);

        long result = db.insert("Texting", null, contentValues);

        return result;
    }

    //Remove row of information
    public void removeRow(int position) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Texting", "_id = ?", new String[] {Long.toString(position)} );

    }


    //Print Cursor
    public void printCursor() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();

        int colIndex = cursor.getColumnIndex(COL_MESSAGE);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String message = cursor.getString(colIndex);
            Log.d("Col names", cursor.getColumnName(colIndex));
            Log.d("Message: ", message);
            cursor.moveToNext();
        }

        Log.d("version Number", Integer.toString(db.getVersion()));
        Log.d("num Cols", Integer.toString(cursor.getColumnCount()));
        Log.d("getCount Num of rows ", Integer.toString(count));
    }


}
