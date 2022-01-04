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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //showing seconds from puzzleActivity
        yourTime = (TextView) findViewById(R.id.your_time);
        Intent getTimeIntent = getIntent();
        String receivedValue = getTimeIntent.getStringExtra("KEY_SEND");
        int userId = getTimeIntent.getIntExtra("userId", 0);
        int level = getTimeIntent.getIntExtra("level", 0);
        Log.w(ScoreActivity.class.getName(), "Level is " + level);
        Log.w(ScoreActivity.class.getName(), "Id is " + userId);
        //yourTime.setText(receivedValue);
        Integer userIdInteger = Integer.valueOf(userId);
        Integer levelInteger = Integer.valueOf(level);
        yourTime.setText(receivedValue);
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

}