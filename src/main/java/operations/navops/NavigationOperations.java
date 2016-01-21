package operations.navops;

/**
 * Created by ford.arnett on 10/2/15.
 */

import com.bottlerocket.webdriver.WebDriverWrapper;
import config.ResourceLocator;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class NavigationOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    public NavOpsChooseFeeds chooseFeeds = new NavOpsChooseFeeds();
    public NavOpsFeatured featured = new NavOpsFeatured();
    public NavOpsShows shows = new NavOpsShows();
    public NavOpsSchedule schedule = new NavOpsSchedule();
    public NavOpsSettings settings = new NavOpsSettings();
    public NavOpsWatchlist watchlist = new NavOpsWatchlist();

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;

        //init nav classes here
        chooseFeeds.init(driverWrapper);
        featured.init(driverWrapper);
        shows.init(driverWrapper);
        schedule.init(driverWrapper);
        settings.init(driverWrapper);
        watchlist.init(driverWrapper);

    }

    public abstract void navigateUsingDrawer(ResourceLocator.DrawerNavigationItem navigationItem);

    public void mainToolbarBack(){
        driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_BACK).click();
    }

    public void mainToolbarLive() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_LIVE).click();
    }

    public void mainToolbarSearch() {
        if(driverWrapper.elements(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH)).size() !=0) {
            driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH).click();
        }
        else {
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS).click();
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH_OVERFLOW).click();
        }
    }

    public void mainToolbarShare() {
        //If there is a share icon handle it this way
        if(driverWrapper.elements(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE)).size() !=0){
            driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE).click();
        }
        //If share is in overflow use this
        else {
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS).click();
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW).click();
        }
    }

    public void mainToolbarWatchlist() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_WATCHLIST).click();
    }

    public void shareFacebook() {
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SHARE_OPTIONS_FACEBOOK).click();
        driverWrapper.find("Post").click();
    }


    /**
     * Returns true iff the main toolbar is found on the current page
     *
     * @return true iff found
     */
    protected abstract boolean mainToolbarVisible();

    /**
     * Open main drawer and make sure it is clickable
     */
    public abstract void openMainDrawerSafe();

    /**
     * Close the main drawer
     */
    public void closeMainDrawer(){
        driverWrapper.getElementByFind(ResourceLocator.device.AWE_MAIN_DRAWER_ANCHOR).click();
    }

    public String getScreenTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID).getText();
    }

    public void genericYesNoPopup(boolean yes) {
        if(yes){
            driverWrapper.getElementByName("Yes").click();
        }
        else{
            driverWrapper.getElementByName("No").click();
        }
    }

    //This may not belong in this class, but it seems silly to have a whole NavOps for splash
    public boolean brandAndSponsorVisible() {
        return driverWrapper.getElementById("awe_splash_brandlogo").isDisplayed() && driverWrapper.getElementById("awe_splash_sponsorlogo").isDisplayed();
    }

    /**
     * Add to watchlist. If already added, remove then add again
     */
    public void addShowToWatchlist() {

        if(driverWrapper.elements(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST)).size() == 0){
            driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST).click();
        }

        driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST).click();


    }
}

