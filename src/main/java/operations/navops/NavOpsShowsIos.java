package operations.navops;

import com.bottlerocket.utils.ErrorHandler;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 2/17/16.
 */
public class NavOpsShowsIos extends NavOpsShows {

    @Override
    public void selectShow(int showNumber){
        //TODO replace this xpath with the label
        WebElement element = driverWrapper.getElementByXpath(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID);
        element.findElements(By.className(ResourceLocatorIos.UIA_BUTTON)).get(showNumber).click();
    }

    @Override
    public boolean showDetailsEpisodesOrClipsSelected() {
        String selected = "1";
        return driverWrapper.getElementValue(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_TAB)).equals(selected);
    }

    @Override
    public String showDetailSelectSeason(int seasonIndex) {
        if(!driverWrapper.checkElementExists(By.name(ResourceLocatorIos.AWE_SHOWS_SEASON_ARROW))){
            ErrorHandler.printErr("There is only one season available ", new Exception());
            return "";
        }

        driverWrapper.getElementByName(ResourceLocatorIos.AWE_SHOWS_SEASON_ARROW).click();
        WebElement element = driverWrapper.getElementByName(ResourceLocatorIos.AWE_SHOWS_SEASONS_TABLE_VIEW);
        //get all of the season cells
        List<WebElement> seasonList = element.findElements(By.className(ResourceLocatorIos.UIA_TABLE_CELL));

        if(seasonList.size() <= seasonIndex){
            ErrorHandler.printErr("Season index chosen is greater than number of available seasons", new IndexOutOfBoundsException("season index OOB"));
            return "";
        }

        WebElement selectedSeason = seasonList.get(seasonIndex);
        selectedSeason.click();

        return selectedSeason.getText();
    }

    @Override
    public void playFromActiveSeason(int index) {
        List<WebElement> clipOrEpisodes = driverWrapper.elements(By.xpath(ResourceLocatorIos.AWE_SHOWS_EPISODE_OR_CLIPS_BUTTONS_XPATH));
        if(clipOrEpisodes.size() <= index){
            ErrorHandler.printErr("Index chosen is greater than number of available episodes/clips", new IndexOutOfBoundsException("video index OOB"));
            return;
        }
        clipOrEpisodes.get(index).click();
    }

}
