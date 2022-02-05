package lv.marmog.androidpuzzlegame;


import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_12;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_15;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_2;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_20;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_24;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_30;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_36;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_4;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_42;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_48;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_56;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_6;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_64;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_72;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_TIMER_RESULT_FOR_9;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.TABLE_TIMER;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import lv.marmog.androidpuzzlegame.database.DatabaseHelper;


/**
 * in this activity the time of the current game is shown
 * and the best result achieved before this game
 */
public class ScoreActivity extends AppCompatActivity {


    private int userId, level, time;
    private String username;
    private long insertResult;
    Cursor cursor;

    //Variables for connection to the database
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_USER_ID,
            DatabaseHelper.COLUMN_TIMER_RESULT_FOR_2,
            DatabaseHelper.COLUMN_TIMER_RESULT_FOR_4,
            COLUMN_TIMER_RESULT_FOR_6,
            DatabaseHelper.COLUMN_TIMER_RESULT_FOR_9,
            DatabaseHelper.COLUMN_TIMER_RESULT_FOR_12,
            COLUMN_TIMER_RESULT_FOR_15,
            COLUMN_TIMER_RESULT_FOR_20,
            COLUMN_TIMER_RESULT_FOR_24,
            COLUMN_TIMER_RESULT_FOR_30,
            COLUMN_TIMER_RESULT_FOR_36,
            COLUMN_TIMER_RESULT_FOR_42,
            COLUMN_TIMER_RESULT_FOR_48,
            COLUMN_TIMER_RESULT_FOR_56,
            COLUMN_TIMER_RESULT_FOR_64,
            COLUMN_TIMER_RESULT_FOR_72,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        // database
        dbHelper = new DatabaseHelper(ScoreActivity.this);
        database = dbHelper.getWritableDatabase();

        // show timer result from puzzleActivity
        TextView yourTime = (TextView) findViewById(R.id.your_time);
        String timeString = String.valueOf(getTime());
        yourTime.setText(timeString + " seconds");

        TextView kidName = (TextView) findViewById(R.id.kid_name);
        username = getUsername();
        Log.i(ScoreActivity.class.getName(), "Username for textview is " + username);
        kidName.setText(username + "!");

        //show best time
        TextView bestTime = (TextView) findViewById(R.id.best_time);
        bestTime.setText(showBestResult() + " seconds");

        // id, level, timer for db
        userId = getUserId();
        level = getLevel();
        time = getTime();
        //Button to go in StartActivity
        //Button to go to the StartActivity
        FloatingActionButton goHome = findViewById(R.id.goHome);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        // insert into db
        insertResult();

    }



    /**
     * inserts timer result in db
     * @return true if the result was saved or false if not
     */
    public boolean insertResult() {
        ContentValues contentValues = new ContentValues();

        // switch for insertion of data in different puzzle complexity/level columns
        switch (level) {
            case 2:
                insertResultToDatabase(contentValues, DatabaseHelper.COLUMN_TIMER_RESULT_FOR_2);
                break;
            case 4:
                insertResultToDatabase(contentValues, DatabaseHelper.COLUMN_TIMER_RESULT_FOR_4);
                break;
            case 6:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_6);
                break;
            case 9:
                insertResultToDatabase(contentValues, DatabaseHelper.COLUMN_TIMER_RESULT_FOR_9);
                break;
            case 12:
                insertResultToDatabase(contentValues, DatabaseHelper.COLUMN_TIMER_RESULT_FOR_12);
                break;
            case 15:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_15);
                break;
            case 20:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_20);
                break;
            case 24:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_24);
                break;
            case 30:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_30);
                break;
            case 36:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_36);
                break;
            case 42:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_42);
                break;
            case 48:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_48);
                break;
            case 56:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_56);
                break;
            case 64:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_64);
                break;
            case 72:
                insertResultToDatabase(contentValues, COLUMN_TIMER_RESULT_FOR_72);
                break;
        }

        if (insertResult == -1) {
            Log.e(ScoreActivity.class.getName(), "Results are not saved");
            return false;
        } else {
            Log.i(ScoreActivity.class.getName(), "Results are saved");
            return true;
        }
    }

    /**
     *inserts result for specific level to the database
     * @param columnTimerResult - timer result for specific level
     */
    private void insertResultToDatabase(ContentValues contentValues, String columnTimerResult) {
        contentValues.put(columnTimerResult, time);
        contentValues.put(DatabaseHelper.COLUMN_USER_ID, userId);
        insertResult = database.insert(TABLE_TIMER, null, contentValues);
        cursor = database.query(TABLE_TIMER, allColumns,
                columnTimerResult + " = " + insertResult, null, null, null, null);
        cursor.moveToLast();
        cursor.close();
        Log.w(ScoreActivity.class.getName(), "Id: " + userId + " and timer result: " + time + " inserted into column " + level);
    }

    /**
     * gets the best result for every level
     * @return
     */
    public String showBestResult(){

        Cursor cursor1;
        int result = 0;

        //switch for showing better result for current level( puzzle pieces quantity)
        switch(getLevel()){
            case 2:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_2);
                break;
            case 4:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_4);
                break;
            case 6:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_6);
                break;
            case 9:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_9);
                break;
            case 12:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_12);
                break;
            case 15:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_15);
                break;
            case 20:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_20);
                break;
            case 24:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_24);
                break;
            case 30:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_30);
                break;
            case 36:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_36);
                break;
            case 42:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_42);
                break;
            case 48:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_48);
                break;
            case 56:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_56);
                break;
            case 64:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_64);
                break;
            case 72:
                result = getBestResult(result, COLUMN_TIMER_RESULT_FOR_72);
                break;
        }

        return String.valueOf(result);
    }

    /**
     * Gets the best result from the database for the specific user for the specific level
     * @param result = the parameter that will be returned
     * @param columnTimerResult - result for specific level
     * @return result- the best result for this specific userid and for the specific level (number of pieces)
     */
    private int getBestResult(int result, String columnTimerResult) {
        Cursor cursor1;
        cursor1 = database.rawQuery("SELECT " + columnTimerResult + " from " +
                TABLE_TIMER + " WHERE user_id " + " = " + getUserId() + " AND " + columnTimerResult + " IS NOT NULL " + " ORDER BY "
                + columnTimerResult + " LIMIT 1 ", null);
        if (cursor1.moveToFirst()) {
            result = cursor1.getInt(0);
        }
        cursor1.close();
        return result;
    }

    /**
     * redirects to ComplexityActivity with extras
     * @param view
     */
    public void startNewGame(View view) {
        Intent intent = new Intent(this, ComplexityActivity.class);
        userId = getUserId();
        username = getUsername();
        intent.putExtra("userIdFromScoreActivity", userId);
        intent.putExtra("usernameFromScoreActivity", username);
        Log.i(ScoreActivity.class.getName(), "User id " + userId + " was sent to complexity activity");
        startActivity(intent);
    }


    /**
     * gets userId, level and time from intent extras
     * @return userId - current user id
     */
    public int getUserId() {
        Intent getUserIntent = getIntent();
        userId = getUserIntent.getIntExtra("userId", 0);
        Log.i(ScoreActivity.class.getName(), "Id is " + userId);
        return userId;
    }

    public String getUsername() {
        Intent getUserIntent = getIntent();
        username = getUserIntent.getStringExtra("username");
        Log.i(ScoreActivity.class.getName(), "Username is " + username);
        return username;
    }

    public int getLevel() {
        Intent getLevelIntent = getIntent();
        level = getLevelIntent.getIntExtra("level", 0);
        Log.i(ScoreActivity.class.getName(), "Level is " + level);
        return level;
    }

    public int getTime() {
        Intent getTimeIntent = getIntent();
        int time = getTimeIntent.getIntExtra("time", 0);
        return time;
    }

    public Cursor getData() {
        Cursor cursor = database.rawQuery("Select * from " + TABLE_TIMER, null);
        return cursor;
    }

    /**
     *  Redirects to StartActivity
     */
    public void goHome() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
