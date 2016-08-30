package operations.navops;

import com.bottlerocket.errorhandling.OperationsException;
import config.ResourceLocator;
import config.ResourceLocatorAndroid;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 2/17/16.
 */
public class NavOpsShowsAndroid extends NavOpsShows {

    /**
     *
     * @param row
     * @param column
     * @throws OperationsException if the row or column provided is outside what is actually on the screen
     */
    @Override
    public void selectShow(int row, int column) throws OperationsException {
        WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID);
        List<WebElement> showsRows = webElement.findElements(By.className(ResourceLocatorAndroid.LINEAR_LAYOUT));
        if(row >= showsRows.size()){
            throw new OperationsException("You have selected a row that doesn't exist on screen " + row + ">=" + showsRows.size());
        }
        List<WebElement> showsColumns = showsRows.get(row).findElements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
        if(column >= showsColumns.size()){
            throw new OperationsException("You have selected a column that doesn't exist on screen " + column + ">=" + showsRows.size());
        }
        WebElement show = showsColumns.get(column);

        show.click();
    }

    @Override
    public boolean showDetailsEpisodesOrClipsSelected() {
        return driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_TAB).isSelected();
    }

    @Override
    public String showDetailSelectSeason(int seasonIndex) {
        //The season select spinner has a different id from the single season heading ids. If it is the season select id, we can proceed
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_SELECT_HEAD))){
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

    @Override
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

    @Override
    public void selectEpisode(int episodeIndex) {
        //TODO flesh out the Android side of this method

//        List<WebElement> clipOrEpisodes = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_CONTAINER).findElements(By.className(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID));
//        clipOrEpisodes.get(episodeIndex).click();
    }


}
