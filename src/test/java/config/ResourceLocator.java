package config;

import com.bottlerocket.config.*;
import com.bottlerocket.webdriverwrapper.uiElementLocator.*;
import org.openqa.selenium.*;

/**
 * Probably needs a more accurate name
 * <p>
 * This class keeps all constants (names, key values, resource locations, etc.) which Appium needs either to find a resource or send a command to the device
 * <p>
 * Created by ford.arnett on 8/31/15.
 */
public class ResourceLocator {
    public static ResourceLocator device;

    public ResourceLocator() {
        //attach all UIElementSelectors to their UIElementLocators
        //NOTE: setElementLocators() does not need to be used here if NOT using UIElementLocator strategy
        setElementLocators();
    }

    //UIElementLocator strategy = define a UIElementLocator and add UIElementSelectors to it

    //Define any other standard variables
    public static final String URL_GOOGLE = "https://www.google.com/";

    //Use ResourceLocatorBundle to quickly define a locator strategy for each platform and select the correct locator strategy at runtime
    public static ResourceLocatorBundle INPUT_TEXT_BOX_GOOGLE_SEARCH = ResourceLocatorBundle.build(By.xpath("//input[@name='q']"), By.id("iOS"), By.className("android"));

    //Use UIElement locator for more flexible locator strategies
    public static UIElementLocator INPUT_BUTTON_GOOGLE_SEARCH = new UIElementLocator();;
    UIElementSelector INPUT_BUTTON_GOOGLE_SEARCH_WEB_XPATH = new UIElementSelector(TestPlatform.WEB, LocatorStrategy.XPATH, "(//input[@name='btnK' and @type='submit'])[2]");

    //Attach all UIElementSelectors to their respective UIElementLocators
    private void setElementLocators() {
//        INPUT_BUTTON_GOOGLE_SEARCH = new UIElementLocator();
        INPUT_BUTTON_GOOGLE_SEARCH.addElementSelector(INPUT_BUTTON_GOOGLE_SEARCH_WEB_XPATH);
        this.INPUT_BUTTON_GOOGLE_SEARCH_WEB_XPATH.setTag("button");

    }
}
