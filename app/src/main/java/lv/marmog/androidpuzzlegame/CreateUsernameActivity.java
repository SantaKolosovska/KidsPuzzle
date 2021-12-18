package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateUsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);
    }

    public void saveNewUsername(View view) {
    startActivity(new Intent(CreateUsernameActivity.this, StartActivity.class));
    }

    public void deleteUsername(View view) {

    }
}