package lv.marmog.androidpuzzlegame.database;

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Assert;

public class UserDAOTest extends TestCase {


    public void testCreateUser() {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        assertTrue(userDAO.createUser(user));
    }

    public void testCheckUsername() {
    }

    public void testDeleteUser() {
    }

    public void testGetAllUsers() {
    }

    public void testGetUserById() {
    }
}