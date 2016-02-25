package operations.navops;

import config.ResourceLocator;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 2/17/16.
 */
public class NavOpsShowsAndroid extends NavOpsShows {
    @Override
    public void selectShow(int i) {
        //Not implemented
    }

    @Override
    public boolean showDetailsEpisodesOrClipsSelected() {
        return driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_TAB).isSelected();
    }

    @Override
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

}
