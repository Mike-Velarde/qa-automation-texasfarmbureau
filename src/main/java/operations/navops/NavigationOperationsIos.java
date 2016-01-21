package operations.navops;

import config.ResourceLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class NavigationOperationsIos extends NavigationOperations {

    @Override
    public void navigateUsingDrawer(ResourceLocator.DrawerNavigationItem navigationItem) {

      //  if(mainToolbarVisible()) {
      //      openMainDrawerSafe();
     //       driverWrapper.getElementByName(navigationItem.toString()).click();
     //   }
    }

    @Override
    protected boolean mainToolbarVisible() {
        return (driverWrapper.elements(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR)).size() != 0);
    }

    @Override
    public void openMainDrawerSafe(){
        //very rough could be redone
        driverWrapper.element(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[10]")).click();
    }
}
