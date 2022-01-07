package lv.marmog.androidpuzzlegame.database;

import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_USER_ID;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.TABLE_TIMER;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> userList;
    private UserDAO userDataSource;

    public UserList(Context c) {
        userList = new ArrayList<User>(0);
        userDataSource = new UserDAO(c);
        userDataSource.open();
    }


    //Methods fot userList interact with the userDAo to get, create, check, delete and return All users
        public User getUser(int userID){
            return userDataSource.getUserById(userID);
        }

        public boolean createUser(User u){
             return userDataSource.createUser(u);
        }
        public boolean checkUsername(String u){
        return userDataSource.checkUsername(u);
}

        public Boolean deleteUser(User u){
          return  userDataSource.deleteUser(u);
        }
    public Boolean deleteResults(User u){
        return  userDataSource.deleteResults(u);
    }

        public List<User> getAllUsers(){
            userList = userDataSource.getAllUsers();
            return userList;
        }

    }

