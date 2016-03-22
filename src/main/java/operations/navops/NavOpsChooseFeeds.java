package operations.navops;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
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
}
