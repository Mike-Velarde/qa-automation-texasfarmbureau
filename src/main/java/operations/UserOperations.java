package operations;

import domod.UserBank;
import webDriver.WebDriverWrapper;

/**
 * Operations involving user authentication or user actions
 *
 * Created by ford.arnett on 9/3/15.
 */
public class UserOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public void signIn(UserBank.User user) {

    }

    public void signOut() {

    }

}
