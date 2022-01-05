package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import lv.marmog.androidpuzzlegame.database.DatabaseHelper;

public class ComplexityActivity extends AppCompatActivity {


    int userId;
    int userIdFromScore;
    int userIdFromPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);

    }

    public void selectPieces(View view) {

        userId = getUserId();

        Intent complexityIntent = new Intent(this, GridViewActivity.class);

        // sending id to gridView activity
        complexityIntent.putExtra("userId", userId);


        // sending number of pieces, columns and rows from buttons to gridView activity
        if (view == findViewById(R.id.choose4)) {
            complexityIntent.putExtra("numberOfPieces", 4);
            complexityIntent.putExtra("numberOfColumns", 2);
            complexityIntent.putExtra("numberOfRows", 2);
        } else if (view == findViewById(R.id.choose9)) {
            complexityIntent.putExtra("numberOfPieces", 9);
            complexityIntent.putExtra("numberOfColumns", 3);
            complexityIntent.putExtra("numberOfRows", 3);
        } else if (view == findViewById(R.id.choose12)) {
            complexityIntent.putExtra("numberOfPieces", 12);
            complexityIntent.putExtra("numberOfColumns", 4);
            complexityIntent.putExtra("numberOfRows", 3);
        }

        startActivity(complexityIntent);

    }

    private int getUserId() {
        Intent getUserId = getIntent();
        // user id from start activity
        userId = getUserId.getIntExtra("userId", 0);
        Log.i(ComplexityActivity.class.getName(), "Received user id from start activity is " + userId);
        // user id from score
        userIdFromScore = getUserId.getIntExtra("userIdFromScoreActivity", 0);
        Log.i(ComplexityActivity.class.getName(), "Received user id from score activity is " + userIdFromScore);
        // user id from popup
        userIdFromPopup = getUserId.getIntExtra("userIdFromPopup", 0);
        Log.i(ComplexityActivity.class.getName(), "Received user id from popup is " + userIdFromPopup);

        // set user id to user id from score
        if (userId == 0) {
            userId = userIdFromScore;
        }
        // if didn't receive the user id from score and it still is 0, set user id from popup
        if (userId == 0) {
            userId = userIdFromPopup;
        }

        return userId;
    }

}