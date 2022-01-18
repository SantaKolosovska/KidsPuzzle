package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



/**
 * This activity enables to choose the complexity level of the game,
 * It has a home button - goHome, extras - userId, userIdFromScore, userIdFromPopup, username.
 * methods: selectPieces, getUserId, getUsername, goHome, setName
 *
 */
public class ComplexityActivity extends AppCompatActivity {

    //Variables that are extras
    private int userId;
    private int userIdFromScore;
    private int userIdFromPopup;
    private String username;
    // redirects to the StartActivity
    private FloatingActionButton goHome;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);
        setName();

        //Button which redirects to the StartActivity
        goHome = findViewById(R.id.goHome);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    /**
     * Selects the number of pieces for puzzle when one of the buttons is pushed (4,9, or 12)
     * and sends extras to GridViewActivity
     * @param view
     */
    public void selectPieces(View view) {

        userId = getUserId();
        username = getUsername();

        Intent complexityIntent = new Intent(this, GridViewActivity.class);

        // sending id to gridView activity
        complexityIntent.putExtra("userId", userId);
        complexityIntent.putExtra("username", username);

        // sending number of pieces, columns and rows from buttons to GridView activity
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

    /**
     * Gets userId from extras
     * @return userId from StartActivity, from ScoreActivity, or from popup
     */
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

        // if not received userId from StartActivity set userIdFromScore
        if (userId == 0) {
            userId = userIdFromScore;
        }
        // if not received userIdFromScore (0) , set userIdFromPopup
        if (userId == 0) {
            userId = userIdFromPopup;
        }

        return userId;
    }

    /**
     * Gets username from extras
     * @return username from StartActivity,  ScoreActivity, or from popup
     */
    private String getUsername() {
        Intent getUsername = getIntent();
        // user id from start activity
        username = getUsername.getStringExtra("username");
        Log.i(ComplexityActivity.class.getName(), "Received username from start activity is " + username);
        // user id from score
        String usernameFromScore = getUsername.getStringExtra("usernameFromScoreActivity");
        Log.i(ComplexityActivity.class.getName(), "Received username from score activity is " + usernameFromScore);
        // user id from popup
        String userNameFromPopup = getUsername.getStringExtra("usernameFromPopup");
        Log.i(ComplexityActivity.class.getName(), "Received username from popup is " + userNameFromPopup);

        // set username to username from score
        if (username == null) {
            username = usernameFromScore;
        }
        // if not received from score, set username from popup
        if (username == null) {
            username = userNameFromPopup;
        }

        return username;
    }


    /**
     * Redirects to StartActivity
     */
    public void goHome() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }


    /**
     * Sets the username in TextView
     */
    public void setName() {
        TextView name = (TextView)findViewById(R.id.username_complexity);
        name.setText(getUsername());
    }
}