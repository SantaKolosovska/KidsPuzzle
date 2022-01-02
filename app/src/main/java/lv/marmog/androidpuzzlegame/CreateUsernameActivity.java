package lv.marmog.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lv.marmog.androidpuzzlegame.database.DatabaseHelper;
import lv.marmog.androidpuzzlegame.database.User;
import lv.marmog.androidpuzzlegame.database.UserDAO;
import lv.marmog.androidpuzzlegame.database.UserList;

public class CreateUsernameActivity extends AppCompatActivity {

//references to buttons and other controls on the layout
    private EditText user;
    private EditText repeatUsername;
    private Button saveNewUsername, deleteUsername;
    private ListView usernamesListView;
    private List<User> usernames;
    private UserList userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);

        userList = new UserList(this);
        //value for variable that is find by id that is created in layout
        user = (EditText) findViewById(R.id.newUsername);
        repeatUsername = (EditText) findViewById(R.id.repeatUsername);
        saveNewUsername = (Button) findViewById(R.id.saveNewUsername);
        deleteUsername = (Button) findViewById(R.id.deleteUsername);

        //creates usernamesList in current layout
        usernamesListView = (ListView) findViewById(R.id.SHOW_ALL_Usernames);
        usernames = new ArrayList<User>(0);
        populateUsernamesList();

        //onClickListener for button to save new username in database
        saveNewUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //variables that have to convert the field of the layout into string
                String username = user.getText().toString();
                String reUser = repeatUsername.getText().toString();

           //check if field are filled and show message if not
                if(username.equals("") || reUser.equals("")) {
                    Toast.makeText(CreateUsernameActivity.this, "Please enter all the field", Toast.LENGTH_SHORT).show();
                }
                //check username and repeatUsername fields if they are same
                else{
                    if(username.equals(reUser)){
                        Boolean checkUser = checkUser(userList.getUser());
                        //check if the username already exists, if no, than insert new username to database
                        if(checkUser == false) {
                            Boolean insert = userList.createUser(username);
                            if (insert == true) {
                                Toast.makeText(CreateUsernameActivity.this, "New username is created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateUsernameActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                            else{
                                Toast.makeText(CreateUsernameActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            }
                    }
                    else{
                        Toast.makeText(CreateUsernameActivity.this, "Username not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        deleteUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User u = userList.getUser(onContextItemSelected());
                userList.deleteUser(u);

            }
        });
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
            int usernameId = usernames.get(position).getUsernameId();
            goToComplexityActivity(usernameId);
        }
    };
    protected void goToComplexityActivity(int id){
        Intent complexityActivity = new Intent(this, ComplexityActivity.class);
        startActivity(complexityActivity);
    }

}