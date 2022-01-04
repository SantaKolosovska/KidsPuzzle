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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);

    }

    public void selectPieces(View view) {

        Intent getUserId = getIntent();
        userId = getUserId.getIntExtra("userId", 0);
        Log.w(ComplexityActivity.class.getName(), "Received user id is " + userId);
        Intent complexityIntent = new Intent(this, GridViewActivity.class);
        complexityIntent.putExtra("userId", userId);
        Log.w(ComplexityActivity.class.getName(), "Sent user id is " + userId);

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

}