package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by ford.arnett on 2/10/16.
 */
public class NavOpsFeaturedIos extends NavOpsFeatured{

    @Override
    public String selectCallToAction(ResourceLocator.CallsToAction callsToAction) throws WebDriverWrapperException {
        //Look for the CTA on the screen.
        MobileElement element = (MobileElement)driverWrapper.element(By.id(callsToAction.toString()));
        driverWrapper.tap(1, element, 100);

        //Not yet possible on iOS due to lack of labels
        //Get the title of the show for the CTA
        //String showTitle = driverWrapper.getElementById(ResourceLocator.device.AWE_FEATURED_CAROUSEL_SHOW_TITLE).getText();

        return "unimplemented";
    }
}
