package operations.navops;

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
public class NavOpsWatchlist implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public void playContinueWatchingShow(int index) {
        MobileElement continueWatchingRow = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_CONTINUE_WATCHING_ROW);
        List<MobileElement> continueThumbnails = continueWatchingRow.findElements(By.id(ResourceLocator.device.AWE_WATCHLIST_SHOW_IMAGE));

        if(index < continueThumbnails.size()){
            continueThumbnails.get(index).click();
        }

        /* TODO This will need more work to swipe beyond the original view. How do we keep track of where we are? On the schedule heading, there was a similar problem,
        however, there we could swipe to a specific element. Can we do that here?
        else{
            continueWatchingRow.swipe(SwipeElementDirection.LEFT, 1000);
        }
        */
    }


    public void playQueueShow(int columnIndex, int rowIndex){
        //TODO add more row and column functionality
        //WebElement queueAndContinueContainer = driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_QUEUE_AND_CONTINUE_CONTAINER);
        //List<WebElement> queueAndContinueRows = queueAndContinueContainer.findElements(By.id("awe_watchlist_showitemgallery"));
        List<WebElement> queueAndContinueRows = driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_ROWS));

        List<WebElement> queueRow = null;
        //TODO this logic goes with the rest of the todo
        //if(rowIndex < queueAndContinueRows.size()){
            queueRow = queueAndContinueRows.get(rowIndex).findElements(By.id("awe_watchlist_itemimage"));
        //}

/*        else{
            //TODO again need to scroll down to get the rows that we can't see
            //continueWatchingRow.swipe(SwipeElementDirection.UP, 1000);
            //queueRow = something otherwise NPE
        }*/
        //Check to see if we can see the given thumbnail index
        if(columnIndex < queueRow.size()){
            queueRow.get(columnIndex).click();
        }
/*        else{
            //TODO again need to scroll down to get the rows that we can't see
            //continueWatchingRow.swipe(SwipeElementDirection.LEFT, 1000);
        }*/

    }

    /**
     * Select queue tab on the watchlist
     */
    public void selectQueueTab(){
        driverWrapper.getElementByName(ResourceLocator.device.AWE_WATCHLIST_QUEUE_TAB).click();
    }

    /**
     * Verify is it QUEUE tab
     * @return true, if it is QUEUE tab
     */
    public boolean isQueueTab(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_LIST)).size()!=0;
    }

    /**
     * Select continue tab on the watchlist
     */
    public void selectContinueTab(){
        driverWrapper.getElementByName(ResourceLocator.device.AWE_WATCHLIST_CONTINUE_TAB).click();
    }

    /**
     * Verifies whether continue tab is exists or not
     * @return true, if continue tab exists
     */
    public boolean hasContinueTab(){
        return driverWrapper.elements(By.name(ResourceLocator.device.AWE_WATCHLIST_CONTINUE_TAB)).size()!=0;
    }

    /**
     * Verifies whether queu tab is exists or not
     * @return true, if queue tab exists
     */
    public boolean hasQueuTab(){
        return driverWrapper.elements(By.name(ResourceLocator.device.AWE_WATCHLIST_QUEUE_TAB)).size()!=0;
    }

    /**
     * Scroll up on the watch detail screen
     */
    public void scrollUp(){
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS);
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 75000);
    }


    /**
     * Tap on the EDIT option
     */
    public void tapEdit(){
        ((MobileElement)driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_EDIT)).tap(1,10);
    }

    /**
     * To select all the watchlist shows
     */
    public void selectAllWatchList(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_QUEUE_EDIT_SELECTALL).click();
    }

    /**
     * Verifies the shows available or not
     * @return true, if shows empty
     */
    public boolean isEmpty(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_EMPTY_MESSAGE)).size()!=0;
    }

    /**
     * To click on remove button
     */
    public void removeShow(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_REMOVE_TITLEBAR).click();
    }

    /**
     * After click the remove, accept the remove in confirmation.
     */
    public void acceptRemove(){
        driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_POPUP_REMOVE).click();
    }

    /**
     * Verify for the confirmation popup
     * @return true, if confirmation popup exists
     */
    public boolean isConfirmationPresent(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_POPUP_REMOVE)).size()!=0;
    }

    /**
     * To remove all the shows
     */
    public void removeAllShows(){
        tapEdit();
        selectAllWatchList();
        removeShow();
        acceptRemove();
    }

    /**
     * To get the shows count
     * @return shows count
     */
    public int getShowsCount(){
        int count=0;
        try {
            tapEdit();
            selectAllWatchList();
            String selectedCount = getHighlightedCount();
            driverWrapper.back();
            count = Integer.parseInt(selectedCount.split(" ")[0]);
        }catch(Exception e){
            //TODO: Add the logger to show the error message
        }
        return count;
    }

    /**
     * To get the highlighted shows count
     * @return highlighted shows count
     */
    public String getHighlightedCount(){
        return driverWrapper.getElementById(ResourceLocator.device.AWE_WATCHLIST_SELECTION_COUNT).getText();
    }


    /**
     * It will verify the shows are in sort order
     * @return true, if shows are in sort order
     */
    public boolean hasShowsSortOrder(){
        List<WebElement> elements=driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS_TITLE));
        String previousTitle="";
        String currentTitle="";
        for(int count=0;count<elements.size();count++){
            currentTitle=elements.get(count).getText();
            if(count>0){
                if(!(previousTitle.compareTo(currentTitle)<0)){
                    return false;
                }
            }
            previousTitle=currentTitle;
        }
        return true;
    }

    /**
     * Verify the contextual bar enabled after click the edit in the menu bar
     * @return true, if contextual bar enabled
     */
    public boolean isContextualBarEnabled(){
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_EDIT_SELECTALL)).size()!=0;
    }


    /**
     * Select the respective show in the shows
     * @param shownnumber
     */
    public void selectShow(int shownnumber){
        List<WebElement> elements=driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOWS));
        elements.get(shownnumber).click();
    }


    public void doLongPressonShow(int shownnumber){
        List<WebElement> elements=driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOWS));
        driverWrapper.longPress(elements.get(shownnumber));
    }


    public String getLastShowTitle(){
        List<WebElement> queueShows=driverWrapper.elements(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS));
        return queueShows.get(queueShows.size()-1).findElement(By.id(ResourceLocator.device.AWE_WATCHLIST_QUEUE_SHOW_DETAILS_TITLE)).getText();
    }






}
