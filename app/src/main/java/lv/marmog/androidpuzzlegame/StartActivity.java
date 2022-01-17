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
import lv.marmog.androidpuzzlegame.database.User;
import lv.marmog.androidpuzzlegame.database.UserList;

/**
 * The activity consists of ListView of usernames and a button create new user
 */
public class StartActivity extends AppCompatActivity {

  private ListView usernamesListView;
  private List<User> usernames;
  private UserList userList;
  private int usernameId;
  private String username;

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

        //value for buttons that is found by id that is created in layout

        Button btnCreateNewUser = (Button) findViewById(R.id.createUsername);

        //setOnClickListener for buttons
        btnCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User username = new User();
                startActivity(new Intent(StartActivity.this, CreateUsernameActivity.class));
            }
        });

    }


    private void populateUsernamesList(){
        //Create a List of Strings
        List<String> userStrings = new ArrayList<String>(0);
        usernames = userList.getAllUsers();

        //enters all the usernames to the array
        for(int i = 0; i<usernames.size(); i++){
            userStrings.add(usernames.get(i).toString());
        }

        //enters them to array adapter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, R.layout.listview_element, userStrings);
        usernamesListView.setAdapter(arrayAdapter);
    }

   //after pushing on the name redirects to complexity activities with extras
    private AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            usernamesListView.setSelection(position);
            view.setSelected(true);
            //get user id of selected user
            usernameId = usernames.get(position).getUsernameId();
            username = usernames.get(position).getUsername();
            Log.i(StartActivity.class.getName(), "Selected username is " + username);
            Log.i(StartActivity.class.getName(), "Selected username id is " + usernameId);
            //make intent
            goToComplexityActivity(usernameId);
        }
    };

    //extras to complexity activity
    protected void goToComplexityActivity(int id){
        Intent goToComplexityActivity = new Intent(this, ComplexityActivity.class);
        goToComplexityActivity.putExtra("userId", usernameId);
        goToComplexityActivity.putExtra("username", username);
        Log.i(StartActivity.class.getName(), "Username id that is sent is " + usernameId);
        Log.i(StartActivity.class.getName(), "Username that is sent is " + username);
        startActivity(goToComplexityActivity);
    }

 }

