package lv.marmog.androidpuzzlegame.database;

public class User {
    private int usernameId;
    private String username;

    //Constructors
    public User() {
    }

    public User(int usernameId, String username) {
        this.usernameId = usernameId;
        this.username = username;
    }

    //toString method for printing the contents of a class object
    @Override
    public String toString() {
        return username ;
    }
//getters and setters
    public int getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(int usernameId) {
        this.usernameId = usernameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
