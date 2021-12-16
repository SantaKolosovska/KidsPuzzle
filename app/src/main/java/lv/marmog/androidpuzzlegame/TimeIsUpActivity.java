package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TimeIsUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_is_up);

    }
    public void tryAgain(View view){
        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Intent);
    }

}