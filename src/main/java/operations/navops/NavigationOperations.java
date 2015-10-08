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

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;

        //init nav classes here
    }

    protected void navigateByIDMulti(String id, int which){
        driverWrapper.driverWait.until(ExpectedConditions.visibilityOf(driverWrapper.element(By.id(id))));
        driverWrapper.elements(By.id(id)).get(which).click();
    }

}

