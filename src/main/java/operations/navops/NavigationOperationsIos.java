package operations.navops;

import config.ResourceLocator;
import config.ResourceLocatorIos;
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
    public void navigateUsingDrawer(ResourceLocator.DrawerNavigationItem navigationItem) {
        //There is no navigating to the featured screen in iOS, no movies or feeds on ios
        if (navigationItem.toString().equals("nb featured") || navigationItem.toString().equals("nb movies") || navigationItem.toString().equals("nb feeds")) {
            return;
        }

            if(mainToolbarClosed()){
            openMainDrawerSafe();
        }

        String navItemName = navigationItem.toString();
        driverWrapper.getElementByName(navItemName).click();

    }

    @Override
    public void mainToolbarBack() {
        driverWrapper.getElementById(ResourceLocatorIos.AWE_MAIN_OVERLAY_CLOSE).click();
    }

    private boolean mainToolbarClosed(){
        return driverWrapper.elements(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_CLOSE_STATE)).size() != 0;
    }

    private boolean mainToolbarOpen(){
        return driverWrapper.elements(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_OPEN_STATE)).size() != 0;
    }

    @Override
    protected boolean mainToolbarVisible() {
        //check if there is a grid open or grid close button
        return driverWrapper.elements(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_OPEN_STATE)).size() != 0 || driverWrapper.elements(By.name(ResourceLocatorIos.AWE_MAIN_DRAWER_CLOSE_STATE)).size() != 0;
    }

    @Override
    public void openMainDrawerSafe(){
        driverWrapper.element(By.name(ResourceLocator.device.AWE_MAIN_DRAWER)).click();
    }

    @Override
    public String getScreenTitle() {
        return driverWrapper.getElementByXpath(ResourceLocatorIos.AWE_MAIN_TITLE_XPATH).getText();
    }

    @Override
    public void mainToolbarSearch() {
        if(mainToolbarClosed()) {
            AutomationOperations.instance().navOp.openMainDrawerSafe();
        }
        driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_SEARCH).click();
    }

    @Override
    public void returnFromShowDetails() {
        driverWrapper.getElementById(ResourceLocatorIos.AWE_SHOW_DEATILS_NAV_BACK_FEATURED).click();
    }
}
