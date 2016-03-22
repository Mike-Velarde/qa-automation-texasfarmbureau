package operations.navops;

import config.ResourceLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by ford.arnett on 2/10/16.
 */
public class NavOpsFeaturedIos extends NavOpsFeatured{

    @Override
    public String selectCallToAction(ResourceLocator.CallsToAction callsToAction){
        //Look for the CTA on the screen. This method can be improved by adding more labels, currently we cannot narrow down this search because we do not have the labels to do so
        driverWrapper.getElementById(callsToAction.toString()).click();

        //Not yet possible on iOS due to lack of labels
        //Get the title of the show for the CTA
        //String showTitle = driverWrapper.getElementById(ResourceLocator.device.AWE_FEATURED_CAROUSEL_SHOW_TITLE).getText();

        return "unimplemented";
    }
}
