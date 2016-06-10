package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocatorIos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class NavOpsWatchlistIos extends NavOpsWatchlist {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    @Override
    public void playQueueShow(int showIndex, int episodeIndex) throws WebDriverWrapperException {

        //TODO add checks to make sure we don't try to click something off screen.

        //Find the main container then use xpath to navigate down to the show and then click it
        driverWrapper.element(By.id(ResourceLocatorIos.AWE_WATCHLIST_QUEUE_MAIN_CONTAINER_IPAD)).findElements(By.xpath(ResourceLocatorIos.AWE_WATCHLIST_MAIN_CONTAINER_TO_SHOW_CELLS_XPATH)).get(showIndex).click();
        //For some reason the hidden CollectionView is causing xpath a lot of problems so (element no longer attached to DOM) I don't know how we can select the episode row only.
        //more ids may fix this. For now, I am looking for the episode containers then dividing by 2 because each show container has two elements with the same id. One is the container, the other the title
        driverWrapper.element(By.id(ResourceLocatorIos.AWE_WATCHLIST_QUEUE_MAIN_CONTAINER_IPAD)).findElements(By.id(ResourceLocatorIos.AWE_WATCHLIST_EPISODE_CONTAINERS)).get(showIndex / 2).click();
    }

    @Override
    public void playContinueWatchingShow(int index) throws WebDriverWrapperException {
        //TODO add checks to make sure we don't try to click something off screen.
        WebElement continueColletionView = driverWrapper.element(By.id(ResourceLocatorIos.AWE_WATCHLIST_CONTINUE_MAIN_CONTAINER)).findElement(By.className(ResourceLocatorIos.UIA_COLLECTION_VIEW));
        continueColletionView.findElements(By.className(ResourceLocatorIos.UIA_COLLECTION_CELL)).get(index).click();

    }

}
