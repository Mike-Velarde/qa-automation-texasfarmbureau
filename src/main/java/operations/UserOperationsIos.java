package operations;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperIos;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import domod.UserBank;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class UserOperationsIos extends UserOperations {

    public void signIn(UserBank.User user, boolean forced) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        //do we need to scroll down for smaller screens?

        if(!inLoginMode()) {
            if (forced) {
                signOut();
            } else {
                return;
            }
        }
        driverWrapper.getElementById(ResourceLocatorIos.AWE_SETTINGS_LOGIN_BUTTON_ID).click();
        driverWrapper.getElementBy(By.id(ResourceLocator.device.OPTIMUM), 60000).click();

        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);

        WebElement userName = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_TEXT_FIELD);
        WebElement password = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_SECURE_TEXT_FIELD);

        //Set username and password
        utils.sendKeysHideKeyboard(userName, user.username);
        utils.sendKeysSafe(password, user.password);

        utils.submitForm();
    }

    /**
     * This method is not as forgiving as it's Android counterpart, for example it does not check that it's on the settings page and then navigate if not.
     *
     * @throws WebDriverWrapperException
     */
    @Override
    public void signOut() throws WebDriverWrapperException {
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocatorIos.AWE_SETTINGS_LOGOUT_BUTTON_ID);
        loginLogoutButton.click();
        AutomationOperations.instance().navOp.genericYesNoPopup(true);
        AutomationOperations.instance().navOp.genericOkPopup();
    }

    /**
     * Check to see if the button is in login or logout mode
     *
     * This may need to be expanded
     *
     * @return True if the button is in "login" mode, that is clicking it will start the login process
     */
    protected boolean inLoginMode() {
        return driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_SETTINGS_LOGIN_BUTTON_ID));
    }

    /**
     * Unlike Android, this must be called from the settings screen
     *
     * @return true if a user is logged in
     */
    public boolean isUserLoggedIn() {
        driverWrapper.setImplicitWait(10, TimeUnit.SECONDS);
        // If this element is there then a user is logged in
        boolean loggedIn = driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_SETTINGS_LOGOUT_BUTTON_ID));
        driverWrapper.restoreImplicitWait();
        return loggedIn;
    }

    /**
     * Not needed for iOS
     *
     * @param aweBrandName
     * @param feedName
     * @param pickFeedServerURLID
     */
    @Override
    public void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID) {}

    public int search(String searchTerm) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.mainToolbarSearch();
        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);
        utils.setTextField(searchTerm);
        //iOS is proving tricky to submit forms, and this one has the done keyboard customized to read Search
        ((WebDriverWrapperIos)driverWrapper).hideKeyboard("Search");

        //TODO labels needed
        //See how many search results there are on the page
        return -1;
    }

    public String getShowDetailsShowTitle() {
        //Can't do this yet because we don't have labels for the titles yet
        return "unimplemented";
    }

}
