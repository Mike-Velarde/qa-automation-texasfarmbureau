package operations.navops;

/**
 * Created by ford.arnett on 10/2/15.
 */

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class NavigationOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    public NavOpsChooseFeeds chooseFeeds = new NavOpsChooseFeeds();
    public NavOpsFeatured featured = createNavOpsFeatured();
    public NavOpsShows shows = createNavOpsShows();
    public NavOpsSchedule schedule = new NavOpsSchedule();
    public NavOpsSettings settings = createNavOpsSettings();
    public NavOpsWatchlist watchlist = new NavOpsWatchlist();

    public abstract NavOpsSettings createNavOpsSettings();
    public abstract NavOpsFeatured createNavOpsFeatured();
    public abstract NavOpsShows createNavOpsShows();

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

    public abstract void mainToolbarBack();

    public abstract void mainToolbarSearch();
    
    public abstract void searchBarBack();
    
   
    /**
     * To know search icon exists or not
     * @return true, if search icon exists
     */
    public abstract boolean hasToolbarSearch();

    public void mainToolbarLive() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_LIVE).click();
    }

    public void mainToolbarShare() {
        //If there is a share icon handle it this way
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE))){
            driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE).click();
        }
        //If share is in overflow use this
        else {
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS).click();
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW).click();
        }
    }
    
    
    /**
     * Tap on the share pop-up
     */
    public void tapMainToolbarShare() {
        //If there is a share icon handle it this way
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE))){
            ((MobileElement)driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE)).tap(1, 10);
        }
        //If share is in overflow use this
        else {
        	
        	((MobileElement)driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).tap(1, 10);
        }
    }

    public void mainToolbarWatchlist() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_WATCHLIST).click();
    }

    
    /**
     * To know the toolbar watchlist exist or not
     * @return true, if watchlist exists
     */
    public boolean hasToolbarWatchlist() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_WATCHLIST));
    }
    
    public void shareFacebook() {
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SHARE_OPTIONS_FACEBOOK).click();
        driverWrapper.find("POST").click();
    }

    
    public boolean isFaceBookExists(){
    	return driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHARE_OPTIONS_FACEBOOK));
    }
    
    /**
     * To know the chromecast is enabled or not 
     * @return true, if chromecast exists 
     */
    public boolean doesChromecastExists(){
        return driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_CHROME_CAST));
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
    
    
    /**
     * To verify the close main drawer exists or not
     * @return true, if close main drawer exists
     */
    public boolean doesCloseMainDrawerExists(){
        return driverWrapper.elementExists(driverWrapper.for_find(ResourceLocator.device.AWE_MAIN_DRAWER_ANCHOR));
    }
    
    /**
     * It will verify for the Hamburger icon
     * @return true, if Hamburger exists
     */
    public boolean hasOpenMainDrawer(){
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_DRAWER));
    }

    public abstract String getScreenTitle();

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
        if(!driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST))){
            driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST).click();
        }
        driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST).click();

    }

    /**
     * The flow to go back from the show details page is different for iOS/Android, so I made a specific method for it
     *
     */
    public abstract void returnFromShowDetails();
    
    /**
     * Remove the show from the watch list
     */
    public void removeFromWatchList(){
        if(!driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST))){
            driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST).click();
        }
    }
    
    /**
     * Tap to add the show to the watch list
     */
    public void tapAddShowToWatchlist() {
        if(!driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST))){
            driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST).click();
        }

        MobileElement element=(MobileElement)driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST);
        element.tap(1, 10);
    }
    
    /**
     * Tap to remove from the watch list
     */
    public void tapRemoveShowToWatchlist() {
        if(!driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST))){
            driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST).click();
        }
        MobileElement element=(MobileElement)driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST);
        element.tap(1, 10);
    }

    /**
     * 
     * @return true, if remove show list exists 
     */
    public boolean hasRemoveShowWatchList(){
  	  return driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST));
    }
    
    /**
     * 
     * @return true, if add show list exists
     */
    public boolean hasAddShowWatchList(){
  	  return driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST));
    }

    /**
     * To verify the navigation item is available in the main drawer
     * @param navigationItem
     * @return true, if navigation item exists
     */
    public boolean hasDrawerItem(String navigationItem){
        return driverWrapper.elementExists(By.name(navigationItem.toString()));
    }
    
    /**
     * Used to scroll up the navigation drawer
     */
    public void navigationDrawerScrollUp(){
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementByName(ResourceLocator.DrawerNavigationItem.movies.toString());
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 1, 0, 5000);
    }
    
        
}

