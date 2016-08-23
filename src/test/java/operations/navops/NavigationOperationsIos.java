package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import io.appium.java_client.MobileElement;
import operations.AutomationOperations;

import org.openqa.selenium.By;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class NavigationOperationsIos extends NavigationOperations {

    @Override
    public NavOpsSettings createNavOpsSettings() {
        return new NavOpsSettingsIos();
    }
    @Override
    public NavOpsFeatured createNavOpsFeatured() {
        return new NavOpsFeaturedIos();
    }
    @Override
    public NavOpsShows createNavOpsShows() {
        return new NavOpsShowsIos();
    }
    @Override
    public NavOpsWatchlist createNavOpsWatchlist() {
        return new NavOpsWatchlistIos();
    }
    @Override
    public NavOpsSchedule createNavOpsSchedule() {
        return new NavOpsScheduleIos();
    }

    @Override
    public void navigateUsingDrawer(ResourceLocator.DrawerNavigationItem navigationItem) throws WebDriverWrapperException {
        //There is no navigating to the featured screen in iOS, no movies or feeds on ios
        String featured = ResourceLocator.DrawerNavigationItem.featured.toString(); String movies = ResourceLocator.DrawerNavigationItem.movies.toString(); String feeds = ResourceLocator.DrawerNavigationItem.feeds.toString();
        if (navigationItem.toString().equals(featured) || navigationItem.toString().equals(movies) || navigationItem.toString().equals(feeds)) {
            return;
        }
        //TODO why am I commented out?
        //if (mainToolbarVisible()) {

            if (mainToolbarClosed()) {
                openMainDrawerSafe();
            }

            String navItemName = navigationItem.toString();
            driverWrapper.getElementByName(navItemName).click();
        //}

    }

    @Override
    public void mainToolbarBack() throws WebDriverWrapperException {
        //driverWrapper.getElementById(ResourceLocatorIos.AWE_MAIN_OVERLAY_CLOSE).click();
        //I was seeing issues on some screens from click
        MobileElement element = (MobileElement) driverWrapper.element(By.id(ResourceLocatorIos.AWE_MAIN_OVERLAY_CLOSE));
        driverWrapper.tap(1, element, 3000);
    }

    protected boolean mainToolbarClosed(){
        //The name doesn't change anymore with the button, so it seems like we can no longer really tell if it is open or closed.
        //Checking to see if the drawer items are displayed should effectively be the same thing
        return !driverWrapper.elements(By.name(ResourceLocatorIos.MAIN_TOOlBAR_GENERIC_NAME)).get(0).isDisplayed();
        //return driverWrapper.elementExists(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_CLOSE_STATE));
    }

    private boolean mainToolbarOpen(){
        return driverWrapper.elementExists(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_OPEN_STATE));
    }

    @Override
    protected boolean mainToolbarVisible() {
        //check if there is a grid open or grid close button
        return driverWrapper.elementExists(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_OPEN_STATE)) || driverWrapper.elementExists(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_CLOSE_STATE));
    }

    @Override
    public void openMainDrawerSafe() throws WebDriverWrapperException {
        driverWrapper.element(By.name(ResourceLocator.device.AWE_MAIN_DRAWER)).click();
    }

    @Override
    public String getScreenTitle() {
        return driverWrapper.getElementByXpath(ResourceLocatorIos.AWE_MAIN_TITLE_XPATH).getText();
    }

    @Override
    public void mainToolbarSearch() throws WebDriverWrapperException {
        if(mainToolbarClosed()) {
            AutomationOperations.instance().navOp.openMainDrawerSafe();
        }
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH).click();
    }

    @Override
    public void returnFromShowDetails() {
        driverWrapper.getElementById(ResourceLocatorIos.AWE_SHOW_DETAILS_NAV_BACK_FEATURED).click();
    }

    @Override
    public void closeVideoPlayer() {
        driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_WINDOW).click();
        driverWrapper.getElementById("Close Video").click();
    }

    @Override
    public void returnFromVideoPlayer() throws WebDriverWrapperException {
        AutomationOperations.instance().userOp.bringUpVideoUI();
        driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_CLOSE).click();
    }

    @Override
    public void searchBarBack() {
        // TODO Have to implement when working on the ios
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SEARCH_BAR_BACK).click();
    }

    @Override
    public boolean hasToolbarSearch() {
        //TODO: Have to test this logic on IOS device
        boolean flag = false;
        if (driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH))) {
            flag = true;
        } else {
            driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS).click();
            flag = driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH_OVERFLOW));
            driverWrapper.back();
        }
        return flag;
    }
}
