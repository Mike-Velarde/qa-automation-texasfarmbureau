package operations.navops;


import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
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


    /**
     * It will launch the about brand in the Settings page
     */
    public void launchAboutBrand(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE_ID).click();
    }

    /**
     * It will launch the FAQ screen
     */
    public void launchFAQ(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE_ID).click();
    }

    /**
     * It will launch the privacy policy screen
     */
    public void launchPrivacyPolicy(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE_ID).click();
    }

    /**
     * It will launch the Terms & Condition screen
     */
    public void launchTermsConditions(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_TERMS_AND_CONDITIONS_TITLE_ID).click();
    }

    /**
     * It will launch the Feedback option
     */
    public void launchFeedback(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_TITLE_ID).click();
    }

    /**
     * It will launch the About BR screen
     */
    public void launchAboutBR(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE_ID).click();
    }

    /**
     * Verify the screen has webview
     * @return true, if screen has web view
     */
    public boolean hasWebView(){
        return driverWrapper.elements(By.className(ResourceLocator.device.AWE_SETTINGS_WEBVIEW)).size()!=0;
    }

    /**
     * It will verify the status of the video over wifi.
     * @return true, if video over wifi is enabled.
     */
    public boolean hasVideoOverWifi(){
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_VIDEO_OVER_WIFI_STATUS).getText().equals(ResourceLocator.device.AWE_SETTINGS_VIDEO_OVER_WIFI_STATUS_YES);
    }

    /**
     * It will verify for the dev options
     * @return true, if developer options exists
     */
    public boolean hasDevOptions(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID)).size()!=0;
    }

    /**
     * It will launch the developer options
     */
    public void launchDevOpts(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID).click();
    }



    /**
     * It will check for the version information
     * @return true, if version info exists
     */
    public boolean hasVersionInfo(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SETTINGS_VERSION_INFO)).size()!=0;
    }

    /**
     * Long press on the version number
     */
    public void doLongPressOnVersion(){
        driverWrapper.longPress(driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_VERSION_INFO));
    }


    /**
     * Verify the more build information
     * @return true, if more build info exists
     */
    public boolean hasBuildInfo(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SETTINGS_BUILD_INFO)).size()!=0;
    }

    /**
     * It will return more about build information
     * @return build information
     */
    public String getBuildInfo(){
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_BUILD_INFO).getText();
    }
    
    public void scrollUp(){
    	MobileElement  episodeClipsContainer = (MobileElement) driverWrapper.getElementByClassName(ResourceLocator.device.AWE_SETTINGS_WEBVIEW);
		episodeClipsContainer.swipe(SwipeElementDirection.UP,1,0, 5000);
    }
  
}
