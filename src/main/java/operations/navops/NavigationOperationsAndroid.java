package operations.navops;

import config.ResourceLocator;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class NavigationOperationsAndroid extends NavigationOperations {

    @Override
    public void openMainDrawerSafe() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_DRAWER_ANCHOR).click();
        //See if the drawer opens properly
        driverWrapper.driverWait.until(ExpectedConditions.elementToBeClickable(By.id(ResourceLocator.device.AWE_MAIN_DRAWER)));

    }

    @Override
    public NavOpsSettings createNavOpsSettings() {
        return new NavOpsSettingsAndroid();
    }
    @Override
    public NavOpsFeatured createNavOpsFeatured() {
        return new NavOpsFeaturedAndroid();
    }
    @Override
    public NavOpsShows createNavOpsShows() {
        return new NavOpsShowsAndroid();
    }

    @Override
    public void navigateUsingDrawer(ResourceLocator.DrawerNavigationItem navigationItem){
        if(mainToolbarVisible()){
            openMainDrawerSafe();
            driverWrapper.getElementByName(navigationItem.toString()).click();

/*            WebDriverWait wait = driverWrapper.newWait(5);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(driverWrapper.getElementByFind(navigationItem.toString())));*/
        }
    }

    protected boolean mainToolbarVisible() {
        return (driverWrapper.elements(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR)).size() != 0);
    }

    public String getScreenTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID).getText();
    }

    public void mainToolbarBack(){
        driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_BACK).click();
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

    @Override
    public void returnFromShowDetails() {
        mainToolbarBack();

    }

    @Override
    public void addShowToWatchlist() {

        if(driverWrapper.elements(By.name(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST)).size() == 0){
            driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST).click();
        }

        driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_ADD_TO_WATCHLIST).click();


    }

}
