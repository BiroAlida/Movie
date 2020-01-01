package com.example.movie.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.MatrixCursor;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import retrofit2.http.Query;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // db.execSQL("Create table user(email text primary key, password text, image blob)");
        db.execSQL("Create table user(email text primary key, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists user");
    }
    // inserting in database

    public boolean insert(String email, String password) {//, byte [] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        //contentValues.put("image", image);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    // checking if the input email exists
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    // checking the email and the password validity in LoginActivity
    public boolean validateEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true; // the input data exists in database
        else return false;

    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    public boolean updatePassword(String newpassword, String email, String oldPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", newpassword);
        db.update("user", contentValues, "password=? and email=?", new String[]{String.valueOf(oldPassword), String.valueOf(email)});
        return true;

    }

    public boolean updateDatabase(String email, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update user set image=? where email=?", new String[]{String.valueOf(image), String.valueOf(email)});
        return true;
    }

    public byte[] getStoredImage(String email) {
        byte[] imageToRetrieve = new byte[1000];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select image from user where email=?", new String[]{String.valueOf(email)});
        //db.execSQL("select image from user where email=?", new String[] {String.valueOf(email)});
        String img = null;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    imageToRetrieve = cursor.getBlob(0);
                }
            } finally {
                cursor.close();
            }

        }
        else{
            imageToRetrieve = null;
        }

        return imageToRetrieve;
    }

}
