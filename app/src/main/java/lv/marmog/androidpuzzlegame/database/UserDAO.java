package lv.marmog.androidpuzzlegame.database;



import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_ID;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.COLUMN_USER_ID;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.TABLE_TIMER;
import static lv.marmog.androidpuzzlegame.database.DatabaseHelper.TABLE_USERS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    //Fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String [] allColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_USERNAME};

    //Constructor


    public UserDAO() {
    }

    public UserDAO(Context context){
        dbHelper = new DatabaseHelper(context);

    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    //Method that insert data into database - creating new username
    public boolean createUser(User username){

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.COLUMN_USERNAME, username.getUsername());
        long insertID = database.insert(TABLE_USERS, null, contentValues);
        Cursor cursor = database.query(TABLE_USERS, allColumns,
                DatabaseHelper.COLUMN_ID + " = " + insertID, null, null, null, null);
        cursor.moveToLast();
        User newUser = cursorToUser(cursor);
        cursor.close();

        if(insertID == -1) {
            Log.e(UserDAO.class.getName(), "New user was NOT created");
            return false;
        }  else {
            Log.i(UserDAO.class.getName(), "New user was created");
            return true;
        }





    }
    //Method that check username in the database
    public Boolean checkUsername(String username){

        Cursor cursor = database.rawQuery("Select * from "+ TABLE_USERS + " where username = ?", new String[] {username});
        if(cursor.getCount()>0){
            return true;}
        else{
            return false;}
    }


    //Method that delete user from the database
    public Boolean deleteUser(User user){

// --- need to delete from timer database
//        String queryStringToDeleteFromTimer = "SELECT * FROM " + TABLE_TIMER + " WHERE " + COLUMN_USER_ID + " = " + user.getUsernameId();
//        Cursor cursor1 =  database.rawQuery(queryStringToDeleteFromTimer, null);


        String queryString = "DELETE FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + " = " + user.getUsernameId();
       Cursor cursor2 =  database.rawQuery(queryString, null);
       if(cursor2.moveToFirst()){
           return true;
       }
       else {
           return false;
       }



    }
    //Method that show us all the users we have in database
    public List<User> getAllUsers(){
        List<User> userList= new ArrayList<User>(0);

        //get data from the db
        Cursor cursor = database.query(TABLE_USERS, allColumns, null,null, null, null, null);
        cursor.moveToFirst();

            //loop through the cursor(result set) and create new usernames objects
            while(!cursor.isAfterLast()){

                User user = cursorToUser(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
        return userList;
    }

    private User cursorToUser(Cursor cursor){
        int id = cursor.getInt(0);
        String username = cursor.getString(1);

        User u = new User();
        u.setUsernameId(id);
        u.setUsername(username);
        return u;

    }

    //method that gives us user by id
    public User getUserById(int id) {

        Cursor cursor = database.query(TABLE_USERS, allColumns, DatabaseHelper.COLUMN_ID + " = " + id, null, null, null, null);
        return (cursor.moveToFirst()) ? cursorToUser(cursor) : null;
    }
}



