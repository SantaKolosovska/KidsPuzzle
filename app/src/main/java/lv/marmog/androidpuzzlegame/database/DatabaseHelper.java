package lv.marmog.androidpuzzlegame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name
    public static final String DATABASE_NAME = "users.db";
    //DB version
    public static final int DATABASE_VERSION = 1;
    //CONSTANT VARIABLES - TABLE AND COLUMNS
    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String TABLE_TIMER = "TIMER";
    public static final String COLUMN_TIMER_RESULT_FOR_2 = "timer_result_for_2";
    public static final String COLUMN_TIMER_RESULT_FOR_4 = "timer_result_for_4";
    public static final String COLUMN_TIMER_RESULT_FOR_6 = "timer_result_for_6";
    public static final String COLUMN_TIMER_RESULT_FOR_9 = "timer_result_for_9";
    public static final String COLUMN_TIMER_RESULT_FOR_12 = "timer_result_for_12";
    public static final String COLUMN_USER_ID = "user_id";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //First time is creating a table for database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Users table with id and username
      String sqlUsers = "CREATE TABLE " + TABLE_USERS + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
              COLUMN_USERNAME + " TEXT UNIQUE);";

      //timer table with foreign key to users id and saving results for every level(puzzle pieces amount)
      String sqlTimer = "CREATE TABLE " + TABLE_TIMER + "(" + COLUMN_TIMER_RESULT_FOR_2 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_4 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_6 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_9 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_12 + " INTEGER, " +
              COLUMN_USER_ID + " INTEGER, FOREIGN KEY(user_id) REFERENCES users(id));";

      db.execSQL(sqlUsers);
      db.execSQL(sqlTimer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    String sqlUsers = "DROP TABLE IF EXISTS " + TABLE_USERS;
    String sqlTimer = "DROP TABLE IF EXISTS " + TABLE_TIMER;

    db.execSQL(sqlTimer);
    db.execSQL(sqlUsers);

    onCreate(db);
    }

//    //Method that check username in the database
//    public Boolean checkUsername(String username){
//
//    SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from "+ TABLE_USERS + " where username = ?", new String[] {username});
//        if(cursor.getCount()>0){
//            return true;}
//        else{
//            return false;}
//    }



}
