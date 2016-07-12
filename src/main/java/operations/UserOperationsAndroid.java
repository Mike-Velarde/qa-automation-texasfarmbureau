package operations;

import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperException;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import config.ResourceLocator;
import config.ResourceLocatorAndroid;
import domod.UserBank;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class UserOperationsAndroid extends UserOperations {

    /**
     * Sign in to allow the playing of restricted content
     *
     *
     * @param user The login credentials
     * @param forced This will force a logout if already signed in and then sign in again. If forced is false and user is already logged in the method will return.
     */
    public void signIn(UserBank.User user, boolean forced) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        //Some devices have the bottom options off screen, this will scroll down for those devices
        driverWrapper.scroll_to(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE);
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        if(!inLoginMode()) {
            if(forced) {
                signOut();
            }
            else {
                return;
            }
        }

        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT).click();
        MobileElement cableProvider = driverWrapper.swipeToElement(ResourceLocator.device.CABLE_PROVIDER_IMAGE_ID, ResourceLocator.device.OPTIMUM_CONTENT_DESC, WebDriverWrapper.AttributeCompareType.contentDesc, 90000, 2);

        cableProvider.click();


        //This part is all in a WebView, we must use find in order to get the elements
        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);
        //make sure WebView is available
        WebElement webView = driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(ResourceLocator.device.WEBKIT_WEBVIEW)));
        //The webView seems to need to be activated in order to get the elements from it

        List<WebElement> authElements = webView.findElements(By.className(ResourceLocatorAndroid.EDIT_TEXT));
        long startTime = System.currentTimeMillis();

        //Keep trying to get web elements until timeout is reached. Sometimes even though the webview is visible it takes a little while to be able to grab the elements
        while(authElements.size() == 0 && (System.currentTimeMillis() - startTime)/(1000) < AutomationConfigProperties.globalWait){
            ((MobileElement) webView).tap(1,2000);
            authElements = webView.findElements(By.className(ResourceLocatorAndroid.EDIT_TEXT));
        }

        //Sometimes we need to wait a little before the web view can be activated. This waits a few seconds and tries to activate the webview again. This probably isn't the best solution, but it should work
        if(authElements.size() == 0){
            driverWrapper.waitLogErr(3000);
            ((MobileElement) webView).tap(1, 2000);
            authElements = webView.findElements(By.className(ResourceLocatorAndroid.EDIT_TEXT));
        }
        //Set username and password
        WebElement usernameElement = authElements.get(0); //driverWrapper.find(ResourceLocator.device.PROVIDER_LOGIN_USERNAME_ID);
        utils.sendKeysHideKeyboard(usernameElement, user.username);
        WebElement passwordElement = authElements.get(1); //driverWrapper.find(ResourceLocator.device.PROVIDER_LOGIN_PASSWORD_ID);
        utils.sendKeysSafe(passwordElement, user.password);

        utils.submitForm();

        //The two different approaches below have seemed unreliable across devices
        //Scroll down so we can see the sign in button, this may not work in all scenarios, needs more testing. The first number is how far away from bottom, and I believe the second is as well
        //((AndroidElement) webView).swipe(SwipeElementDirection.UP,350, 150, 2000);
        //webView.findElements(By.className(ResourceLocator.device.IMAGE_VIEW)).get(2).click();
        //This doesn't work on all devices
        //driverWrapper.find(ResourceLocator.device.PROVIDER_SIGN_IN_BUTTON).click();

        //Wait for toolbar, then check to see that provider logo is now in toolbar
        driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR)));
    }

    public void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID){
        //Check if we are on feature screen
        if(AutomationOperations.instance().navOp.featured.isOnPage())
            return;

        AutomationOperations.instance().navOp.chooseFeeds.chooseBrandToPickFeed(aweBrandName);
        AutomationOperations.instance().navOp.chooseFeeds.pickFeedToAWEHome(feedName);
        selectFeed(pickFeedServerURLID);
    }

    private void selectFeed(String pickFeedServerURLId) {
        driverWrapper.getElementById(pickFeedServerURLId).click();
    }

    public int search(String searchTerm) {
        AutomationOperations.instance().navOp.mainToolbarSearch();
        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);
        WebElement search = driverWrapper.getElementById(ResourceLocator.device.AWE_SEARCH_BAR_ENTER_TEXT);
        utils.sendKeysSafe(search, searchTerm);
        //Hit enter key, not sure why this is needed here but send keys doesn't trigger the search
        driverWrapper.pressKeyCode(ResourceLocator.device.KEYCODE_ENTER);

        //See how many search results there are on the page
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SEARCH_RESULTS)).size();
    }

    public String getShowDetailsShowTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE).getText();
    }
}
