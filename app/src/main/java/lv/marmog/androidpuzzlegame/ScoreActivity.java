package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView congratulationText;
    TextView yourTimeText;
    TextView yourTime;
    TextView BestTimeText;
    TextView BestTime;
    Button next;
    Button exit;
    int userId;
    int level;
    int time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //showing seconds from puzzleActivity
        yourTime = (TextView) findViewById(R.id.your_time);
//        Intent getTimeIntent = getIntent();
//        String receivedValue = getTimeIntent.getStringExtra("KEY_SEND");
//        int userId = getTimeIntent.getIntExtra("userId", 0);
//        int level = getTimeIntent.getIntExtra("level", 0);

        String time = getTimeString();

        //yourTime.setText(receivedValue);
        Integer userIdInteger = Integer.valueOf(userId);
        Integer levelInteger = Integer.valueOf(level);
        yourTime.setText(time);
    }
    public void startNewGame(View view){
        Intent intent = new Intent(this, ComplexityActivity.class);// redirect from this page to MainActivity page- list of images
        startActivity(intent);
    }

    public void goToMenu(View view){
        // redirect from this activity to the first activity, for now it redirects to MianActivy!!!!!!!!!!!!!!!!
        Intent intent = new Intent(this, ComplexityActivity.class);
        startActivity(intent);
    }

    public int getUserId() {
        Intent getUserIntent = getIntent();
        userId = getUserIntent.getIntExtra("userId", 0);
        Log.w(ScoreActivity.class.getName(), "Id is " + userId);
        return userId;
    }

    public int getLevel() {
        Intent getLevelIntent = getIntent();
        level = getLevelIntent.getIntExtra("level", 0);
        Log.w(ScoreActivity.class.getName(), "Level is " + level);
        return level;
    }

    public String getTimeString() {
        Intent getTimeIntent = getIntent();
        String time = getTimeIntent.getStringExtra("KEY_SEND");
        Log.w(ScoreActivity.class.getName(), "Timer result is " + time);
        return time;
    }

    public int getTimeInt() {
        String timeString = getTimeString();
        time = Integer.valueOf(timeString);
        return time;
    }


}