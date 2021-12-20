package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startGame(View view) {
        startActivity(new Intent(StartActivity.this, ComplexityActivity.class));

    }

    public void createUsername(View view) {
        startActivity(new Intent(StartActivity.this, CreateUsernameActivity.class));
    }


}