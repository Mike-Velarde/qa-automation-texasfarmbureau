package automationtests.testingautomationtests;

import com.bottlerocket.webdriverwrapper.uiElementLocator.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;
import testmain.*;

import java.net.*;

import static config.ResourceLocator.*;

public class UiTest extends TestMain {

    /*
    QUICK UI Test to display ResourceLocator usage
    URL_GOOGLE: standard String stored in ResourceLocator
    INPUT_TEXT_BOX_GOOGLE_SEARCH: By from ResourceLocatorBundle
    INPUT_BUTTON_GOOGLE_SEARCH: By from UIElementLocator
     */
    @Test()
    public void quickUiTest() throws MalformedURLException {

        URL url = new URL(URL_GOOGLE);
        By input_google_search = INPUT_TEXT_BOX_GOOGLE_SEARCH.getBy();
        By input_button_google_search = INPUT_BUTTON_GOOGLE_SEARCH.getLocator(LocatorStrategy.XPATH);
        By input_button_google_search_1 = INPUT_BUTTON_GOOGLE_SEARCH.getLocator("button");

        am.driverWrapper.navigateTo(url);
        am.driverWrapper.getElementBy(input_google_search, 5).sendKeys("what do people google?");
        am.driverWrapper.getElementBy(input_button_google_search, 5).click();
    }
}
