package lv.marmog.androidpuzzlegame.database;

import android.content.Context;

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

        public User createUser(User u){
             return userDataSource.createUser(u);
        }

        public void checkUser(User u){
            userDataSource.checkUsername(u);
        }
        public void deleteUser(User u){
            userDataSource.deleteUser(u);
        }

        public List<User> getAllUsers(){
            userList = userDataSource.getAllUsers();
            return userList;
        }

    }

