package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lv.marmog.androidpuzzlegame.database.DatabaseHelper;
import lv.marmog.androidpuzzlegame.database.User;
import lv.marmog.androidpuzzlegame.database.UserDAO;
import lv.marmog.androidpuzzlegame.database.UserList;

public class StartActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
  private Button btnLogin;
  private Button btnCreateNewUser;
  private ListView usernamesListView;
  private List<User> usernames;
  private UserList userList;
  int usernameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    //creates usernamesList in current layout
        usernames = new ArrayList<User>(0);
        userList= new UserList(this);

        usernamesListView = (ListView) findViewById(R.id.list_usernames);

        populateUsernamesList();
        usernamesListView.setOnItemClickListener(listViewListener);

        //value for buttons that is find by id that is created in layout
        btnLogin = (Button) findViewById(R.id.StartGame);
        btnCreateNewUser = (Button) findViewById(R.id.createUsername);

        //setOnClickListener for buttons
        btnCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User username = new User();
                startActivity(new Intent(StartActivity.this, CreateUsernameActivity.class));
            }
        });

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StartActivity.this, ComplexityActivity.class);
//
//                startActivity(intent);
//            }
//        });

    }
    private void populateUsernamesList(){
        //Create a List of Strings
        List<String> userStrings = new ArrayList<String>(0);
        usernames = userList.getAllUsers();

        for(int i = 0; i<usernames.size(); i++){
            userStrings.add(usernames.get(i).toString());

        }
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userStrings);
        usernamesListView.setAdapter(arrayAdapter);
    }

    private AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            usernamesListView.setSelection(position);
            view.setSelected(true);
            //get user id of selected user
            usernameId = usernames.get(position).getUsernameId();
            Log.w(StartActivity.class.getName(), "Username id is " + usernameId);
            //make intent
            goToComplexityActivity(usernameId);
        }
    };

    protected void goToComplexityActivity(int id){
        Intent goToComplexityActivity = new Intent(this, ComplexityActivity.class);
        goToComplexityActivity.putExtra("userId", usernameId);
        Log.w(StartActivity.class.getName(), "Username id that is sent is " + usernameId);
        startActivity(goToComplexityActivity);
    }



 }

