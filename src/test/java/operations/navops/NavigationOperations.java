package operations.navops;


import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;


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

