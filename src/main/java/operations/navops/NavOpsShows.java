package operations.navops;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import config.ResourceLocatorAndroid;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;

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
		MobileElement  episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_CONTAINER);
		episodeClipsContainer.swipe(SwipeElementDirection.UP,1,0, 5000);
		/*MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_FEATURED_INFO);
		for(int count=0;count<10;count++){
			episodeClipsContainer.swipe(SwipeElementDirection.UP,1,10,10000);
		}*/
		/*MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_EPISODES_CLIPS_MAIN_CONTAINER);
        episodeClipsContainer.swipe(SwipeElementDirection.DOWN, 5000);*/
    }


	/**
	 * It will scroll up the screen in the Shows screen
	 */
	public void scrollToBottom_Shows(){
		MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS);
		episodeClipsContainer.swipe(SwipeElementDirection.UP,75000);
	}

	public abstract void playFromActiveSeason(int index);

	/**
	 * It will return the season title 
	 * @return seasontitle
	 */
	public String getSeasonTitle() {
		if(driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_TITLE)).size()!=0){
			return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_TITLE).getText();
		}else{
			return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_STATIC_TEXT).getText();
		}
	}

	/**
	 * It will scroll up the grid to the half of the device 
	 */
	public void scrollHalfToTop(){
		MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_FOREGROUND_CONTAINER);
		episodeClipsContainer.swipe(SwipeElementDirection.UP,1,0, 5000);

	}


	//TODO: Need to verify the behavior of this execution logic in other devices 
	/**	
	 * It will scroll up the grid from half positon to the top position.
	 */
	public void scrollToHalf() {
		MobileElement episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_FEATURED_INFO);
		for(int i=0;i<8;i++){
			episodeClipsContainer.swipe(SwipeElementDirection.UP, 1, 10, 1700);
		}
	}

	/**
	 * Navigate to shows and return the shows header
	 */
	public String navigateToShows(){
		driverWrapper.back();
		return driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID).getText();
	}


	/**
	 * Select the clips tab and return the navigation result
	 */
	public boolean verifyClipAssets(){
		selectClipsTab();
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_CLIP_LIST)).size()>0 || clipsEmpty();
	}

	/**
	 * Select the episode tab and return the navigation result
	 */
	public boolean verifyEpisodeAssets(){
		selectEpisodesTab();
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_LIST_CONTAINER)).size()>0 || episodesEmpty();
	}

	/**
	 * After clicking the More option, returns the content is availability status
	 */
	public boolean verifyContentFeature(String option){
		WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_MORE_LINK);
		if(webElement.getText().equals(option)){
		    webElement.click();
		}
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_MORE_CONTENT_DESCRIPTION)).size()!=0;
	}


	/**
	 * Verify the MORE link exists
	 * @return true, if MORE link exists
	 */
	public boolean hasMore(){
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_MORE_LINK)).size()!=0;
	}

	/**
	 * Swipe left in the Episode tab
	 */
	public void swipeLeft_Episode(){
		MobileElement episodeClipsContainer =null;
		if(!episodesEmpty()){
			episodeClipsContainer =(MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_THUMBNAILS);
		}else{
			episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_EMPTY_MESSAGE_ID);
		}
		episodeClipsContainer.swipe(SwipeElementDirection.LEFT, 1,1,1000);
	}

	/**
	 * Swipe right on the clips tab
	 */
	public void swipeRight_Clips(){
		MobileElement episodeClipsContainer = null;
		if(!clipsEmpty()){
			episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_THUMBNAILS);
		}else{
			episodeClipsContainer = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_CLIP_EMPTY_MESSAGE_ID);
		}
		episodeClipsContainer.swipe(SwipeElementDirection.RIGHT,1,10,1000);
	}

	/**
	 * return the selection status of the clips tab 
	 */
	public boolean isClipsTab(){
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_CLIP_LIST)).size() != 0 || clipsEmpty();
	}

	/**
	 * return the selection status of the episodes tab 
	 */
	public boolean isEpisodeTab(){
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_LIST_CONTAINER)).size() != 0 || episodesEmpty();
	}

	/**
	 *It will check the show has multiple seasons or single season based on that it will return the no. of seasons available for that show.
	 */
	public abstract int getSeasonsCount();

	/**
	 * Launches the video
	 */
	public void launchVideo(int clipNo){
		List<WebElement> videos=driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_THUMBNAILS));
		MobileElement element=(MobileElement)videos.get(clipNo);
		element.tap(1, 10);
	}

	/**
	 * returns the videos count on the screen
	 */
	public int getVideosCount(){
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_THUMBNAILS)).size();
	}



	/**
	 * Verify the respective season related videos are displaying or not
	 */
	public boolean verifySeasons(){
		int seasonCount=getSeasonsCount();
		for(int count=0;count<seasonCount;count++){
			String seasonText="";
			if(seasonCount==1){
				seasonText=driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SEASON_STATIC_TEXT).getText();
			}else{
				seasonText=showDetailSelectSeason(count);
			}
			launchVideo(0);
			String episodeSeasonText=driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_EPISODE_DURATION).getText();
			driverWrapper.back();
			//Getting the season text from the show in the show details screen
			if(!episodeSeasonText.split(",")[0].equals(seasonText)){
				try {
					driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "ShowDetails_Episode_Season_NotMatched_" + System.currentTimeMillis());
				}catch(Exception e){
					ErrorHandler.printErr("error taking screenshot",e);
				}
				return false;
			}
		}
		return true; 
	}



	/**
	 * Returns the respective season episodes are displaying or not 	
	 */
	public boolean verifySeasonEpisodes(){
		selectEpisodesTab();
		if(episodesEmpty()){
			return true; 
		}else{
			return verifySeasons();
		}

	}

	/**
	 * Returns the respective season clips are displaying or not 	
	 */
	public boolean verifySeasonClips(){
		selectClipsTab();
		if(clipsEmpty()){
			return true; 
		}else{
			return verifySeasons();
		}
	}


	/**
	 * Return the date exists on the video or not
	 */
	public boolean isAirDateExists(){
		return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_AIRDATE)).size()!=0;
	}

	/**
	 * It will return the date from the show details show screen.
	 */
	public String getAirDate(){
		return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_AIRDATE).getText().split("\\|")[0].replace("Aired on", "").trim();
	}

	/**
	 * Verifies the first date is after the second date
	 * @param dateString1
	 * @param dateString2
	 * @return true, if first date is after the second date
	 */
	public boolean verifyDate(String dateString1,String dateString2){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date1 = sdf.parse(dateString1);
			Date date2 = sdf.parse(dateString2);
			return date1.compareTo(date2)>0;
		}catch(Exception e){
			return false;
		}
	}


	/**
	 * returns the clip parent title
	 */
	public String getClipParentTitle(){
		return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_VIDEO_PARENT_TITLE).getText();
	}


	/**
	 * Used to get the shows count in the shows tab
	 * @return count of shows
	 */
	public int getShowsCount() {
		int showsCount=0;
		WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID);
		List<WebElement> showsRows = webElement.findElements(By.className(ResourceLocatorAndroid.LINEAR_LAYOUT));
		for(int count=0;count<showsRows.size();count++) {
			List<WebElement> showsColumns = showsRows.get(count).findElements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
			showsCount=showsCount+showsColumns.size();
		}
		return showsCount;
	}

	/**
	 * verify the shows arranged in alphabetical order or not
	 * @return true, if shows arranged in alphabetical order
	 */
	public boolean hasShowsArrangedAlphabetically(){
		String previousTitle="";
		String currentTitle="";
		int count=0;
		WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID);
		List<WebElement> showsRows = webElement.findElements(By.className(ResourceLocatorAndroid.LINEAR_LAYOUT));
		for(int row=0;row<showsRows.size();row++) {
			List<WebElement> showsColumns = showsRows.get(row).findElements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
			for (int col = 0; col < showsColumns.size(); col++) {
				showsColumns.get(col).click();
				currentTitle = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE).getText();
				if (count==0) {
					if (!(currentTitle.compareTo(previousTitle) > 0)) {
						return false;
					}
				}
				previousTitle = currentTitle;
				count++;
				driverWrapper.back();
			}
		}
		return true;
	}

	/**
	 * Get the column count in the shows screen
	 * @return count of columns
	 */
	public int getShowsColumnCount(){
		try{
			WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_SHOWS_CONTAINER_GRID);
			List<WebElement> showsRows = webElement.findElements(By.className(ResourceLocatorAndroid.LINEAR_LAYOUT));
			List<WebElement> showsColumns = showsRows.get(0).findElements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
			return showsColumns.size();
		}catch(Exception e){

		}
		return 0;
	}

	/**
	 * Select multiple shows to test at a time only one show has to display
	 * @return true, if only one show display or it has only one show
	 */
	public boolean selctMultipleShows(){
		List<WebElement> shows = driverWrapper.elements(By.id(ResourceLocator.device.AWE_SHOWS_VIDEO_THUMBNAILS));
		if(shows.size()>=1){
			shows.get(0).click();
			String firstTitle=driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE).getText();
			driverWrapper.back();
			((MobileElement)shows.get(0)).tap(1, 0);
			try{
				((MobileElement)shows.get(1)).tap(1, 0);
			}catch(Exception e){

			}
			String currentTitle=driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE).getText();
			return currentTitle.equals(firstTitle);
		}
		return true;
	}
}
