package operations.navops;

/**
 * Created by ford.arnett on 10/2/15.
 */

import com.bottlerocket.webdriver.WebDriverWrapper;
import operations.AutomationOperationsListener;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public class NavigationOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;

        //init nav classes here
    }

}

