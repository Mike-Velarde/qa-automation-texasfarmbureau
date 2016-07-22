package operations.navops;

import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.utils.ErrorHandler;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 2/17/16.
 */
public class NavOpsShowsIos extends NavOpsShows {

    /**
     * This is a little weird with the unused parameter on iOS, if there is a better way to do this it should be refactored
     *
     * @param showNumber
     * @param unusedOnIos
     * @throws OperationsException
     */
    @Override
    public void selectShow(int showNumber, int unusedOnIos) throws OperationsException {
        WebElement element = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_CONTAINER);
        element.findElements(By.className(ResourceLocatorIos.UIA_COLLECTION_CELL)).get(showNumber).click();
    }

    @Override
    public boolean showDetailsEpisodesOrClipsSelected() {
        String selected = "1";
        return driverWrapper.getElementValue(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_TAB)).equals(selected);
    }

    @Override
    public String showDetailSelectSeason(int seasonIndex) {
        if (!driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_SHOWS_SEASON_SELECT_HEADER))) {
            ErrorHandler.printErr("There is only one season available ", new Exception());
            return "";
        }

        driverWrapper.getElementByName(ResourceLocatorIos.AWE_SHOWS_SEASON_SELECT_HEADER).click();
        List<WebElement> seasonList = driverWrapper.elements(By.id(ResourceLocatorIos.AWE_SHOWS_SEASON_SELECT_OPTIONS));

        //Each item in the list has two matches of the resource locator. Each item seems to have the index you expect, but then repeats.
        //For example in a list of 4 it seems like the first choice is at index 0 and index 4
        int seasonListCount = seasonList.size() / 2;
        if (seasonListCount <= seasonIndex) {
            ErrorHandler.printErr("Chosen " + seasonIndex + " is greater than the " + seasonListCount + " available", new IndexOutOfBoundsException("season index OOB"));
            return "";
        }

        WebElement selectedSeason = seasonList.get(seasonIndex);
        selectedSeason.click();

        return selectedSeason.getText();
    }

    @Override
    public void playFromActiveSeason(int index) throws OperationsException {
        List<WebElement> clipOrEpisodes = driverWrapper.getElementById(ResourceLocatorIos.AWE_SHOWS_MEDIA_CONTAINER).findElements(By.className(ResourceLocatorIos.UIA_COLLECTION_CELL));
        //first UIACollectionCell is not a show but the shows toolbar, so remove it to leave only shows
        clipOrEpisodes.remove(0);

        if (clipOrEpisodes.size() <= index) {
            throw new OperationsException("Chosen index of " + index + " is greater than the " + clipOrEpisodes.size() + " available", new IndexOutOfBoundsException("video index OOB"));
        }

        //This actually seems to work even if the index is off screen.
        clipOrEpisodes.get(index).click();

        List<WebElement> showDeatilContainers = driverWrapper.elements(By.id(ResourceLocatorIos.AWE_SHOW_DETAIL_ASSET_DETAIL_CONTAINER));
        MobileElement videoThumbnailContainer = (MobileElement) showDeatilContainers.get(index).findElement(By.id(ResourceLocatorIos.AWE_SHOW_DETAIL_ASSET_DETAIL_PLAY_BUTTON));
        //Tapping here seems more reliable than click
        videoThumbnailContainer.tap(1, 1000);
    }
}
