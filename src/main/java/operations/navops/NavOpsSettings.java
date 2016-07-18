package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import config.ResourceLocatorAndroid;
import config.ResourceLocatorIos;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
     * To verify the Settings options
     * 
     * @param title,
     *            the settings option title
     * @return true, if title exists
     */
    public boolean hasSettingsOption(String title) {
        return driverWrapper.elementExists(By.id(title));
    }

    /**
     * Test a web endpoint and get the HTTP response code
     * 
     * @param url
     *            URL to check
     * @return HTTP response code
     */
    public int testWebContentEndpoint(String url) {
        return WebsiteConnection.ping(url, 10000);
    }

    public String getGmailComposeTitle() {
        // this is somewhat gimmicky since we are looking for the name and returning the name, but there aren't many better options. At least we know it's there if this works
        return driverWrapper.getElementByName(ResourceLocator.device.GMAIL_COMPOSE_TEXT).getText();
    }

    /**
     * After selecting a settings option, return back to the settings page. This currently makes sense for Android and not iOS
     */
    public abstract void backFromSettingsOption() throws WebDriverWrapperException;

    /**
     * Verify the screen has webview
     * 
     * @return true, if screen has web view
     */
    public boolean hasWebView() {
        return driverWrapper.elementExists(By.className(ResourceLocator.device.AWE_SETTINGS_WEBVIEW));
    }

    /**
     * It will verify the status of the video over wifi.
     * 
     * @return true, if video over wifi is enabled.
     */
    public boolean hasVideoOverWifi() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_VIDEO_OVER_WIFI_STATUS).getText().equals(ResourceLocator.device.AWE_SETTINGS_VIDEO_OVER_WIFI_STATUS_YES);
    }

    /**
     * It will verify for the dev options
     * 
     * @return true, if developer options exists
     */
    public boolean hasDevOptions() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID));
    }

    /**
     * It will launch the developer options
     */
    public void launchDevOpts() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID).click();
    }

    /**
     * It will check for the version information
     * 
     * @return true, if version info exists
     */
    public boolean hasVersionInfo() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SETTINGS_VERSION_INFO));
    }

    /**
     * Long press on the version number
     */
    public void doLongPressOnVersion() {
        driverWrapper.longPress(driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_VERSION_INFO));
    }

    /**
     * Verify the more build information
     * 
     * @return true, if more build info exists
     */
    public boolean hasBuildInfo() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SETTINGS_BUILD_INFO));
    }

    /**
     * It will return more about build information
     * 
     * @return build information
     */
    public String getBuildInfo() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_BUILD_INFO).getText();
    }

    /**
     * Settings list options scroll up
     */
    public void settingsOptionsScrollUp() {
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_TITLE_ID);
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 1, 0, 75000);
    }

    /**
     * Used to scroll up the webview
     */
    public void webviewScrollUp() {
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementByClassName(ResourceLocator.device.AWE_SETTINGS_WEBVIEW);
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 1, 0, 75000);
    }

    /**
     * After selecting the feedback option from the settings it will popup the more details screen. This method used to click the email on the more screen.
     */
    public void clickOnEmail() {
        WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_MORE_SCREEN);
        List<WebElement> items = webElement.findElements(By.id(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_MORE_SCREEN_OPTIONS));
        for (int count = 0; count < items.size(); count++) {
            if (ResourceLocator.device.AWE_SETTINGS_FEEDBACK_EMAIL.contains(items.get(count).getText().toLowerCase())) {
                items.get(count).click();
                break;
            }
        }
    }

    public boolean verifyFeedbackScreen() {
        return driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_SETTINGS_EMAIL_FORM_SEND));
    }
}
