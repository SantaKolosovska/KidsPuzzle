package lv.marmog.androidpuzzlegame.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Creating and updateing 2 tables in the database -
 * table 1 - user id and username
 * table 2 - results dor every level with user id
 */
//public class  DatabaseHelper extends SQLiteOpenHelper {
//    //Database name
//    public static final String DATABASE_NAME = "users.db";
//    //DB version
//    public static final int DATABASE_VERSION = 1;
//    //CONSTANT VARIABLES - TABLE AND COLUMNS
//    public static final String TABLE_USERS = "USERS";
//    public static final String COLUMN_ID = "ID";
//    public static final String COLUMN_USERNAME = "USERNAME";
//    public static final String TABLE_TIMER = "TIMER";
//    public static final String COLUMN_TIMER_RESULT_FOR_2 = "timer_result_for_2";
//    public static final String COLUMN_TIMER_RESULT_FOR_4 = "timer_result_for_4";
//    public static final String COLUMN_TIMER_RESULT_FOR_6 = "timer_result_for_6";
//    public static final String COLUMN_TIMER_RESULT_FOR_9 = "timer_result_for_9";
//    public static final String COLUMN_TIMER_RESULT_FOR_12 = "timer_result_for_12";
//    public static final String COLUMN_USER_ID = "user_id";
//
//    public DatabaseHelper(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    public class  DatabaseHelper extends SQLiteOpenHelper {
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
    public static final String COLUMN_TIMER_RESULT_FOR_15 = "timer_result_for_15";
    public static final String COLUMN_TIMER_RESULT_FOR_20 = "timer_result_for_20";
    public static final String COLUMN_TIMER_RESULT_FOR_24 = "timer_result_for_24";
    public static final String COLUMN_TIMER_RESULT_FOR_30 = "timer_result_for_30";
    public static final String COLUMN_TIMER_RESULT_FOR_36 = "timer_result_for_36";
    public static final String COLUMN_TIMER_RESULT_FOR_42 = "timer_result_for_42";
    public static final String COLUMN_TIMER_RESULT_FOR_48 = "timer_result_for_48";
    public static final String COLUMN_TIMER_RESULT_FOR_56 = "timer_result_for_56";
    public static final String COLUMN_TIMER_RESULT_FOR_64 = "timer_result_for_64";
    public static final String COLUMN_TIMER_RESULT_FOR_72 = "timer_result_for_72";
        public static final String COLUMN_USER_ID = "user_id";

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    //Creates a table for database.
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
              COLUMN_TIMER_RESULT_FOR_15 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_20 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_24 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_30 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_36 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_42 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_48 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_56 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_64 + " INTEGER, " +
              COLUMN_TIMER_RESULT_FOR_72 + " INTEGER, " +
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

        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + " , which will destroy all old data");
    }

}
