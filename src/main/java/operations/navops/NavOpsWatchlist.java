package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 11/17/15.
 */
public abstract class NavOpsWatchlist implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public abstract void playContinueWatchingShow(int index) throws WebDriverWrapperException;

    public abstract void playQueueShow(int columnIndex, int rowIndex) throws WebDriverWrapperException;

    /**
     * Select queue tab on the watchlist
     */
    public void selectQueueTab() {
        driverWrapper.getElementByName(ResourceLocator.device.AWE_WATCHLIST_QUEUE_TAB).click();
    }

    /**
     * Verify is it QUEUE tab
     * 
     * @return true, if it is QUEUE tab
     */
    public boolean isQueueTab() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_LIST));
    }

    /**
     * Select continue tab on the watchlist
     */
    public void selectContinueTab() {
        driverWrapper.getElementByName(ResourceLocator.device.AWE_WATCHLIST_CONTINUE_TAB).click();
    }

    /**
     * Verifies whether continue tab is exists or not
     * 
     * @return true, if continue tab exists
     */
    public boolean hasContinueTab() {
        return driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_WATCHLIST_CONTINUE_TAB));
    }

    /**
     * Verifies whether queue tab is exists or not
     * 
     * @return true, if queue tab exists
     */
    public boolean hasQueueTab() {
        return driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_WATCHLIST_QUEUE_TAB));
    }

    /**
     * Scroll up on the watch detail screen
     */
    public void scrollUp() {
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS);
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 75000);
    }

    /**
     * Tap on the EDIT option
     */
    public void tapEdit() {
        ((MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_EDIT)).tap(1, 10);
    }

    /**
     * To select all the watchlist shows
     */
    public void selectAllWatchList() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_QUEUE_EDIT_SELECT_ALL).click();
    }

    /**
     * Verifies the shows available or not
     * 
     * @return true, if shows empty
     */
    public boolean isEmpty() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_WATCHLIST_EMPTY_MESSAGE));
    }

    /**
     * To click on remove button
     */
    public void removeShow() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_REMOVE_TITLEBAR).click();
    }

    /**
     * After click the remove, accept the remove in confirmation.
     */
    public void acceptRemove() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_POPUP_REMOVE).click();
    }

    /**
     * Verify for the confirmation popup
     * 
     * @return true, if confirmation popup exists
     */
    public boolean isConfirmationPresent() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_WATCHLIST_POPUP_REMOVE));
    }

    /**
     * To remove all the shows
     */
    public void removeAllShows() {
        tapEdit();
        selectAllWatchList();
        removeShow();
        acceptRemove();
    }

    /**
     * To get the shows count
     * 
     * @return shows count
     */
    public int getShowsCount() {
        int count = 0;
        try {
            tapEdit();
            selectAllWatchList();
            String selectedCount = getHighlightedCount();
            driverWrapper.back();
            count = Integer.parseInt(selectedCount.split(" ")[0]);
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    /**
     * To get the highlighted shows count
     * 
     * @return highlighted shows count
     */
    public String getHighlightedCount() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_SELECTION_COUNT).getText();
    }

    /**
     * It will verify the shows are sorted in alphabetically or not.
     * 
     * @return true, if shows are in sorted in alphabetically.
     */
    public boolean hasShowsSortOrder() {
        List<WebElement> elements = driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS_TITLE));
        String previousTitle = "";
        String currentTitle = "";
        for (int count = 0; count < elements.size(); count++) {
            currentTitle = elements.get(count).getText();
            if (count > 0) {
                if (!(previousTitle.compareTo(currentTitle) < 0)) {
                    return false;
                }
            }
            previousTitle = currentTitle;
        }
        return true;
    }

    /**
     * Verify the contextual bar enabled after click the edit in the menu bar
     * 
     * @return true, if contextual bar enabled
     */
    public boolean isContextualBarEnabled() {
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_EDIT_SELECT_ALL));
    }

    /**
     * Select the respective show in the shows
     * 
     * @param showsNumber
     */
    public void selectShow(int showsNumber) {
        List<WebElement> elements = driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOWS));
        elements.get(showsNumber).click();
    }

    /**
     * Long press on the particular show
     * 
     * @param showsNumber
     */
    public void doLongPressonShow(int showsNumber) {
        List<WebElement> elements = driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOWS));
        driverWrapper.longPress(elements.get(showsNumber));
    }

    /**
     * @return the last show title
     */
    public String getLastShowTitle() {
        List<WebElement> queueShows = driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS));
        return queueShows.get(queueShows.size() - 1).findElement(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS_TITLE)).getText();
    }
}
