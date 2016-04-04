package operations.navops;


import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import operations.AutomationOperationsListener;
import webinteractions.WebsiteConnection;

/**
 * Created by ford.arnett on 11/17/15.
 */
public abstract class NavOpsSettings implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }


    public void navigateToSettingsOption(String title) {
        driverWrapper.getElementById(title).click();
    }

    /**
     * Test a web endpoint and get the HTTP response code
     * @param url URL to check
     * @return HTTP response code
     */
    public int testWebContentEndpoint(String url){
        return WebsiteConnection.ping(url, 10000);
    }

    public String getGmailComposeTitle() {
        //this is somewhat gimmicky since we are looking for the name and returning the name, but there aren't many better options. At least we know it's there if this works
        return driverWrapper.getElementByName(ResourceLocator.device.GMAIL_COMPOSE_TEXT).getText();
    }

    /**
     * After selecting a settings option, return back to the settings page. This currently makes sense for Android and not iOS
     */
    public abstract void backFromSettingsOption();
}
