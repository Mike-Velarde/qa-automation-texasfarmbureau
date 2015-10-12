package operations.navops;

/**
 * Created by ford.arnett on 10/2/15.
 */

import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import webDriver.WebDriverWrapper;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public class NavigationOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;
    ErrorHandler
    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;

        //init nav classes here
    }

}

