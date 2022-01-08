package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lv.marmog.androidpuzzlegame.database.DatabaseHelper;
import lv.marmog.androidpuzzlegame.database.UserDAO;

public class ComplexityActivity extends AppCompatActivity {

    //Variables that is extras
    int userId;
    int userIdFromScore;
    int userIdFromPopup;
    String username;
    //Button to go to the StartActivity
    private FloatingActionButton goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);
        setName();

        //Button to go to the StartActivity
        goHome = findViewById(R.id.goHome);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

    }
//Method that selects pieces for puzzle when is pressed one of the buttons
    public void selectPieces(View view) {

        userId = getUserId();
        username = getUsername();

        Intent complexityIntent = new Intent(this, GridViewActivity.class);

        // sending id to gridView activity
        complexityIntent.putExtra("userId", userId);
        complexityIntent.putExtra("username", username);


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

    // --- method to get user id
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

    private String getUsername() {
        Intent getUsernameIntent = getIntent();
        String username = getUsernameIntent.getStringExtra("username");
        Log.i(ComplexityActivity.class.getName(), "Received username is " + username);
        return username;
    }

    //Method to go to the StartActivity
    public void goHome() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void setName() {
        TextView name = (TextView)findViewById(R.id.username_complexity);
        name.setText(getUsername());
    }
}