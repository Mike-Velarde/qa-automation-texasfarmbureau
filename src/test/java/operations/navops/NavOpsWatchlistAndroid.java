package operations.navops;

import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class NavOpsWatchlistAndroid extends NavOpsWatchlist {
    @Override
    public void playQueueShow(int columnIndex, int rowIndex) {
        // TODO add more row and column functionality
        // WebElement queueAndContinueContainer = driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_QUEUE_AND_CONTINUE_CONTAINER);
        // List<WebElement> queueAndContinueRows = queueAndContinueContainer.findElements(By.id("awe_watchlist_showitemgallery"));
        List<WebElement> queueAndContinueRows = driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_ROWS));

        List<WebElement> queueRow = null;
        // TODO this logic goes with the rest of the todo
        // if(rowIndex < queueAndContinueRows.size()){
        queueRow = queueAndContinueRows.get(rowIndex).findElements(By.id("awe_watchlist_itemimage"));
        // }

        /*
         * else{ //TODO again need to scroll down to get the rows that we can't see //continueWatchingRow.swipe(SwipeElementDirection.UP, 1000); //queueRow = something otherwise NPE }
         */
        // Check to see if we can see the given thumbnail index
        if (columnIndex < queueRow.size()) {
            queueRow.get(columnIndex).click();
        }
        /*
         * else{ //TODO again need to scroll down to get the rows that we can't see //continueWatchingRow.swipe(SwipeElementDirection.LEFT, 1000); }
         */

    }

    public void playContinueWatchingShow(int index) {
        MobileElement continueWatchingRow = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_CONTINUE_WATCHING_ROW);
        List<MobileElement> continueThumbnails = continueWatchingRow.findElements(By.id(ResourceLocator.device.AWE_WATCHLIST_SHOW_IMAGE));

        if (index < continueThumbnails.size()) {
            continueThumbnails.get(index).click();
        }

        /*
         * TODO This will need more work to swipe beyond the original view. How do we keep track of where we are? On the schedule heading, there was a similar problem, however, there we could swipe to a specific element. Can we do that here?
         * else{ continueWatchingRow.swipe(SwipeElementDirection.LEFT, 1000); }
         */
    }

}
