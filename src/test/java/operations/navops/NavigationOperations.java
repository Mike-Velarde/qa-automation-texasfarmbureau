package operations.navops;


import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import operations.AutomationOperationsListener;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class NavigationOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    //NavOps classes here

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;

        //init nav classes here

    }

}

