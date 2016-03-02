package operations.navops;

import com.bottlerocket.webdriver.WebDriverWrapper;
import config.ResourceLocator;
import config.ResourceLocatorAndroid;
import config.ResourceLocatorIos;
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
public abstract class NavOpsShows implements AutomationOperationsListener {
    protected WebDriverWrapper driverWrapper;

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
        List<WebElement> showsRows = webElement.findElements(By.className(ResourceLocatorAndroid.LINEAR_LAYOUT));
        List<WebElement> showsColumns = showsRows.get(row).findElements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
        WebElement show = showsColumns.get(column);

        show.click();
    }

    public abstract void selectShow(int i);

    public abstract boolean showDetailsEpisodesOrClipsSelected();

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
    public abstract String showDetailSelectSeason(int seasonIndex);

    public void scrollToBottom() {
        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById("awe_showdetail_featuredinfo");
        episodeClipsContainer.swipe(SwipeElementDirection.UP, 75000);

/*        MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_EPISODES_CLIPS_MAIN_CONTAINER);
        episodeClipsContainer.swipe(SwipeElementDirection.DOWN, 5000);*/
    }

    public abstract void playFromActiveSeason(int index);

    public String getSeasonTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_TITLE).getText();
    }
}
