package operations.navops;

import org.openqa.selenium.By;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;

import config.ResourceLocator;
import operations.AutomationOperationsListener;

/**
 * Created by ford.arnett on 10/15/15.
 */
public class NavOpsChooseFeeds implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public void choseBrandToPickFeed(String brandName){
        driverWrapper.getElementByFind(brandName).click();
    }

    public void pickFeedToAWEHome(String feedName){
        driverWrapper.getElementByFind(feedName).click();
    }
    
    /**
     * Verify the Feed page has pick feed list
     * @return true, if pick feed list exists.
     */
    public boolean hasPickFeedList(){
    	return driverWrapper.elements(By.id(ResourceLocator.device.AWE_FEED_PICK_LIST)).size()!=0;
    }
}
