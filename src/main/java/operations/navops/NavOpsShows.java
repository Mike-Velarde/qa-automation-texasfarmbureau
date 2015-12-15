package operations.navops;

import com.bottlerocket.webdriver.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperations;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 10/21/15.
 */
public class NavOpsShows implements AutomationOperationsListener {
    private WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    /**
     * This currently doesn't account for selecting a row that isn't seen on the device
     * @param row
     * @param column
     */
    public void selectShow(int row, int column) {
        WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID);
        List<WebElement> showsRows = webElement.findElements(By.className(ResourceLocator.device.LINEAR_LAYOUT));
        List<WebElement> showsColumns = showsRows.get(row).findElements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
        WebElement show = showsColumns.get(column);

        show.click();
    }

    public boolean showDetailsEpisodesOrClipsSelected() {
        return driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_TAB).isSelected();
    }

    /**
     * Checks to see if the empty episode message is displaying, meaning there are no episodes.
     *
     * @return if the episode list is empty
     */
    public boolean episodesEmpty() {
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_EMPTY_MESSAGE_ID)).size() != 0;
    }

    public boolean clipsEmpty() {
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_CLIP_EMPTY_MESSAGE_ID)).size() != 0;
    }

    public void selectEpisodesTab(){
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_TAB).click();
    }

    public void selectClipsTab(){
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_CLIPS_TAB).click();
    }

    /**
     * Select the given index in the season list, first checking to make sure there are actually multiple seasons
     *
     * @param seasonIndex the index of the season in the array, NOT the season itself
     * @return number of seasons in the list
     */
    public String showDetailSelectSeason(int seasonIndex) {
        //The season select spinner has a different id from the single season heading ids. If it is the season select id, we can proceed
        if(driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_SELECT_HEAD)).size() != 0){
            driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_SELECT_HEAD).click();
            List<WebElement> seasonList = driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_SELECT_SEASON));
            //Prevent array OOB
            if(seasonIndex <= seasonList.size()) {
                seasonList.get(seasonIndex).click();
                return getSeasonTitle();
            }

            return "";
        }

        //can't change seasons so return empty string
        return "";
    }

    public void scrollToBottom() {
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById("awe_showdetail_featuredinfo");
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 75000);

/*        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_EPISODES_CLIPS_MAIN_CONTAINER);
        episodeClipsContainer.swipe(SwipeElementDirection.DOWN, 5000);*/
    }

    public void playFromActiveSeason(int index) {
        //TODO handle clips as well, just need to change to check for size == 0 on episodes and if it is 0 then grab clips using awe_showdetail_cliplist instead of awe_showdetail_episodelist

        WebElement videoRows = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_LIST_CONTAINER);
        List<WebElement> episodes = videoRows.findElements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_THUMBNAILS));

        if(index < episodes.size()){
            episodes.get(index).click();
        }
        /*
        else {
            scroll down to see more videos, then select current one
        }

         */
        //TODO doesn't seem to play when parts of the screen load before others
        AutomationOperations.instance().userOp.videoDetailsPlayVideo();
    }

    public String getSeasonTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_TITLE).getText();
    }
}
