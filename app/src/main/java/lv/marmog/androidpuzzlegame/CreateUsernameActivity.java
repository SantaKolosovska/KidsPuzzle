package lv.marmog.androidpuzzlegame;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import lv.marmog.androidpuzzlegame.database.DatabaseHelper;
import lv.marmog.androidpuzzlegame.database.User;
import lv.marmog.androidpuzzlegame.database.UserList;

/**
 * This activity creates a new username or deletes the existing one
 * It consists of 2 EditText fields for usernames, that should be the same - user and repeatUsername;
 * saveNewUsername and deleteUsername buttons
 * and List of usernames - usernames
 * Also it has a home button which redirects to StartActivity
 */
public class CreateUsernameActivity extends AppCompatActivity {

//references to buttons and other controls on the layout
    private int usernameId;
    private int idToDelete;
    private EditText user;
    private EditText repeatUsername;
    private Button saveNewUsername, deleteUsername;
    private FloatingActionButton goHome;
    private ListView usernamesListView;
    private List<User> usernames;
    private UserList userList;
    private DatabaseHelper db;

    /**
     *
      * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);

        userList = new UserList(this);

        user = (EditText) findViewById(R.id.enter_username);
        repeatUsername = (EditText) findViewById(R.id.repeat_username);
        saveNewUsername = (Button) findViewById(R.id.save_username);
        deleteUsername = (Button) findViewById(R.id.delete_username);
        goHome = findViewById(R.id.goHome);


        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        //creates usernamesList in current layout
        usernamesListView = (ListView) findViewById(R.id.view_usernames_listview);
        usernames = new ArrayList<>(0);
        populateUsernamesList();
        db = new DatabaseHelper(this);

        //onClickListener for button to save new username in database
        saveNewUsername.setOnClickListener(new View.OnClickListener() {
            /**
             * Saving new username performs check if both fields contain the same strings (new ones)
             * using checkUsernameAndDisplayToastMessage method
             * @param v - View
             */
            @Override
            public void onClick(View v) {

                String username = user.getText().toString();
                String reUser = repeatUsername.getText().toString();
                User u = new User();
                u.setUsername(username);

                //check if fields are filled correctly and shows a toast message if not
                checkUsernameAndDisplayToastMessage(username, reUser, u);
            }
        });


        usernamesListView.setOnItemClickListener(listViewListener);


        deleteUsername.setOnClickListener(new View.OnClickListener() {
            /**
             * Deletes username from the database and all the scores for this user pushing the button deleteUsername
             */
            @Override
            public void onClick(View v) {
                    //delete only user by usernameId from TABLE_USERS
                Log.i(CreateUsernameActivity.class.getName(), "Username to be deleted is: " + getIdToDelete());
//
                User user = new User();
                user.setUsernameId(getIdToDelete());
                userList.deleteResults(user); //deletes all the results
                userList.deleteUser(user); //after results are deleted deletes the user
                Toast.makeText(CreateUsernameActivity.this,"User was deleted", Toast.LENGTH_LONG).show();
                populateUsernamesList();

            }
        });
    }

    /**
     * checks if both fields are filled and with the same username ,
     * which doesn't exist in the database
     * @param username - username for the first (EditView) field user
     * @param reUser - username for the second (EditView) field repeatUsername
     * @param u Object User with already set username
     */
    private void checkUsernameAndDisplayToastMessage(String username, String reUser, User u) {
        if(username.equals("") || reUser.equals("")) {
            Toast.makeText(CreateUsernameActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        }

        //check username and repeatUsername fields if they are same
        else{
            if(username.equals(reUser)){
                //First one if the method is taken from databaseHelper.
                Boolean checkUser = userList.checkUsername(username);
                //check if the username already exists in the db, if no, insert it
                if(!checkUser) {
                  Boolean insert = userList.createUser(u);
                    if (insert) {
                        Toast.makeText(CreateUsernameActivity.this, "New username is created", Toast.LENGTH_LONG).show();
                        populateUsernamesList();
                        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateUsernameActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                }
                    else{
                        Toast.makeText(CreateUsernameActivity.this, "User already exists", Toast.LENGTH_LONG).show();
                    }
            }
            else{
                Toast.makeText(CreateUsernameActivity.this, "Usernames are not matching", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * populates all the users existing in the database
     */
    private void populateUsernamesList(){
        //Create a List of Strings
        List<String> usernameStrings = new ArrayList<>(0);
        usernames = userList.getAllUsers();

        for(int i = 0; i<usernames.size(); i++){
            usernameStrings.add(usernames.get(i).toString());
        }

        //sets the adapter for ListView
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernameStrings);
        usernamesListView.setAdapter(arrayAdapter);
    }

    /**
     * chooses the username to de deleted from the ListView
     */
    private AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            usernamesListView.setSelection(position);
            view.setSelected(true);
            //get user id of selected user
            usernameId = usernames.get(position).getUsernameId();
            Log.i(CreateUsernameActivity.class.getName(), "Selected username id is " + usernameId);
            setIdToDelete(usernameId);
            Log.i(CreateUsernameActivity.class.getName(), "idToDelete is set to  " + usernameId);
        }
    };

    private int setIdToDelete(int id) {
        idToDelete = id;
        return idToDelete;
    }

    public int getIdToDelete() {
        return idToDelete;
    }

    /**
     *Redirects to StartActivity
     */
    public void goHome() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}