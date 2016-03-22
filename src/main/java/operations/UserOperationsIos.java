package operations;

import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperIos;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import domod.UserBank;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class UserOperationsIos extends UserOperations {

    public void signIn(UserBank.User user){
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        //do we need to scroll down for smaller screens?

        //Is this check necessary?
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        if(!inLoginMode())
            signOut();
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT).click();
        driverWrapper.getElementById(ResourceLocator.device.AWE_LOGIN_CONTINUE).click();
        driverWrapper.getElementById(ResourceLocator.device.OPTIMUM).click();

        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);

        WebElement userName = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_TEXT_FIELD);
        WebElement password = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_SECURE_TEXT_FIELD);

        //Set username and password
        utils.sendKeysHideKeyboard(userName, user.username);
        utils.sendKeysSafe(password, user.password);

        utils.submitForm();

        //TODO update this for iOS
        //Wait for toolbar, then check to see that provider logo is now in toolbar
        //driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR)));
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

    public int search(String searchTerm) {
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
