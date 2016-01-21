package operations.navops;

import config.ResourceLocator;
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
}
