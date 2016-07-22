package operations.navops;

import config.ResourceLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by ford.arnett on 2/10/16.
 */
public class NavOpsFeaturedAndroid extends NavOpsFeatured{

    @Override
    public String selectCallToAction(ResourceLocator.CallsToAction callsToAction){
        WebElement featureContainer = driverWrapper.getElementById(ResourceLocator.device.AWE_FEATURED_CTA_CONTAINER);

        //Find the call to action we are looking for
        WebElement ctaElement = featureContainer.findElement(By.name(callsToAction.toString()));
        //Get the title of the show for the CTA
        String showTitle = driverWrapper.getElementById(ResourceLocator.device.AWE_FEATURED_CAROUSEL_SHOW_TITLE).getText();
        ctaElement.click();

        return showTitle;
    }
}
