package domod;

import config.AppDefaults;

import java.util.ArrayList;

/**
 * Created by ford.arnett on 10/5/15.
 */
public class UserBank {

    private static final String DEFAULT_USER_NAME = "testymctester44@gmail.com";
    private static final String DEFAULT_USER_PASSWORD = "testing44";

    public final User defaultUser = new User(DEFAULT_USER_NAME, DEFAULT_USER_PASSWORD);
    public ArrayList<User> users = new ArrayList<User>();

    public class User{
        public String username;
        public String password;

        public User(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
}