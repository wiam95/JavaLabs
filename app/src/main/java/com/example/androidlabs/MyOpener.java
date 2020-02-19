package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ContactsDB";
    protected final static int VERSION_NUM = 1;

    public final static String TABLE_NAME = "Texting";

    public final static String COL_MESSAGE = "MESSAGE";
    public final static String COL_TYPE = "TYPE";
    public final static String COL_ID = "ID";

    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGE + "text,"
                + COL_TYPE  + "integer,"
                + COL_ID + "integer);" );  // add or remove columns
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
    public boolean insertMessage(String msg, String type, long id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_MESSAGE, msg);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_ID, id);

        long result = db.insert("Texting", null, contentValues);

        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }

    //View data
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }


}
