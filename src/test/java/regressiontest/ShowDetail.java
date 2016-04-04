package regressiontest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.ErrorHandler;

import assertions.AssertionLogger;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 18/03/2016.
 */
public class ShowDetail extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testShowDetails(){

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        
        //Verify the the portrait mode screen appears as mentioned in the confluence
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_portrait_mode_matches_confluence_" + System.currentTimeMillis());

        
        //Navigate to the back
        driverWrapper.rotate();
        
        //Verify the the landscape mode screen appears as mentioned in the confluence
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_landscape_mode_matches_confluence_" + System.currentTimeMillis());
      
        driverWrapper.rotate();
       
        //Verify the scroll functionality and ensure the behavior of the screen
        verifyScrollFunctionality();
        
        //Verify the tabs
        testShowDetailsDefaultTab();
        
        //Verify the tab assets as each tab contains specific elements or not
        verifyTabAssets();
        
        
        //Verify the season functionality
        verifySeasons();
        
        //Verify show feed information
        verifyShowsFeed();

        //Verify for the default tab. It will be clips if no videos available in the episodes.
        verifyDefaultTab();
        
         //Verify the More and Less functionality
        verifyMoreFunctionality();
        
        //Testing the watch list functionalities
        testWatchList();
        
        
        //Test whether all the episodes in sorting order or not
        verifyEpisodesSort();
        
        //Verify the screen orientation change functionalities
        verifyRotateFunctionalities();
        
        //Share in the facebook
        verifyShareFunctionality();
    }
    
  
   /*
    * Verifies the watch list functionality
    */
    public void testWatchList(){
    	 //Watch list tests
    	driverWrapper.back();
    	AutomationOperations.instance().navOp.shows.selectShow(0,0);
    	 //Remove from the watch list
    	 AutomationOperations.instance().navOp.removeFromWatchList();
    	 
        driverWrapper.back();
        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        
        AutomationOperations.instance().navOp.shows.selectShow(0,0);
       
        //Adding to the watch list 
        AutomationOperations.instance().navOp.tapAddShowToWatchlist();
        
        //Verify watch list status
        assertionLogger.setTestType("Test if remove watch list is appearing");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasRemoveShowWatchList());

        
        //Verify watch list status
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_'-'_symbol_to_ensure_removeShowWatchList_" + System.currentTimeMillis());
        
        driverWrapper.back();

        int addedCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        //Verify whether the show added to the watch list or not
        assertionLogger.setTestType("Test the show added to the watch list or not");
        assertionLogger.assertEquals(watchCount+1,addedCount);

        watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        AutomationOperations.instance().navOp.shows.selectShow(0,0);
        
        //Remove the show from watchlist
        AutomationOperations.instance().navOp.tapRemoveShowToWatchlist();
        
        //Verify watch list status
        assertionLogger.setTestType("Test if add watch list is appearing");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasAddShowWatchList());

        //Removing the show from watch list
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_'+'_symbol_to_ensure_addShowWatchList_" + System.currentTimeMillis());
        
        driverWrapper.back();
        
        int removedCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        //Verify whether the show added to the watch list or not
        assertionLogger.setTestType("Test the show added to the watch list or not");
        assertionLogger.assertEquals(watchCount,removedCount+1);
        
        //Rotate the device
        driverWrapper.rotate();
        int rotateCount=AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        
        assertionLogger.setTestType("Test the watch list count will remain same when rotate the device");
        assertionLogger.assertEquals(removedCount,rotateCount);
        
    }

    /**
     * TODO a change has seemed to make showDetailsEpisodesOrClipsSelected no longer work, need to investigate
     *
     * Verify that the correct tab on the show details screen has been defaulted to by checking the criteria for clips.
     * If the criteria for clips has not been met, then it should be episodes
     */
    private void testShowDetailsDefaultTab() {
    	
    	//Select the show
        AutomationOperations.instance().navOp.shows.selectShow(0,0);

        boolean episodesOrClips = AutomationOperations.instance().navOp.shows.showDetailsEpisodesOrClipsSelected();

        //Find out what if episodes and/or clips are empty
        AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        boolean episodesEmpty = AutomationOperations.instance().navOp.shows.episodesEmpty();
        AutomationOperations.instance().navOp.shows.selectClipsTab();
        boolean clipsEmpty = AutomationOperations.instance().navOp.shows.clipsEmpty();

        //If it defaults to clips, make sure that episodes are empty and clips are not
        if(!episodesOrClips){
            assertionLogger.assertTrue(episodesEmpty && !clipsEmpty);
            AutomationOperations.instance().navOp.shows.selectClipsTab();
        }
        //If it defaults to episodes, make sure the criteria (episodes empty, clips not) for defaulting to clips is not met
        else{
            assertionLogger.assertFalse(episodesEmpty && !clipsEmpty);
            AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        }
    }
    
    /*
     * Verifies the scrolling functionality
     */
    public void verifyScrollFunctionality(){
    	 //Verify the AWE place holder image
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_AWE_Place_Holder_Image_" + System.currentTimeMillis());
        
        AutomationOperations.instance().navOp.shows.selectShow(0,0);
        
        //Move to the half of the screen
        AutomationOperations.instance().navOp.shows.scrollToHalf();
        
        //Verify the background color fade in or not also verify the Snipe, Show title, Subtitle, MORE link, Sponser Image fade to black 
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_activity_circle_while_loading_Videos_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "ShowDetails_HalfScrolling_FadeIn_Image_" + System.currentTimeMillis());
        
        
        //Verify the scroll bottom functionalities in episode and clips tab
        verifyVerticalScroll();
        
        //Navigate to the back and verify the shows screen
        assertionLogger.setTestType("Test whther user able to navigate to back or not");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.navigateToShows(),"Shows");
        
        
        //Verify for the sponsor logo
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_Sponser_Logo_" + System.currentTimeMillis());
    }
    
    /*
     * Verifies the respective tab has respective assets or not
     */
    public void verifyTabAssets(){
        //Verify Episode tab contains all the episodes only
        assertionLogger.setTestType("Test Episode tab contains all episodes or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyEpisodeAssets());
        
        //Verify the episode tab contains all the episodes or not
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "ShowDetails_Episode_Assets_Image_" + System.currentTimeMillis());
        
        //Verify Clips tab contains all the clips only
        assertionLogger.setTestType("Test Clips tab contains all clips or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyClipAssets());

        //Verify the clip tab contains all the clips or not
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "ShowDetails_Clip_Assets_Image_" + System.currentTimeMillis());

    }
    
    //TODO: Need to modify the logic if no season present
    /*
     * Verifies the respective season videos are displaying or not
     */
    public void verifySeasons(){
    	 assertionLogger.setTestType("Test the Episodes are displaying as per the season");
         assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifySeasonEpisodes());
         assertionLogger.setTestType("Test the Clips are displaying as per the season");
         assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifySeasonClips());
        
    }
     
    /*
     * Verifies the show feed information on the videos
     */
    public void verifyShowsFeed(){
    	driverWrapper.back();
    	AutomationOperations.instance().navOp.shows.selectShow(0,0);
    	String showTitle=AutomationOperations.instance().userOp.getShowDetailsShowTitle();
    	AutomationOperations.instance().navOp.shows.selectEpisodesTab();
    	if(AutomationOperations.instance().navOp.shows.episodesEmpty()){
        	AutomationOperations.instance().navOp.shows.selectClipsTab();
          	 assertionLogger.setTestType("Test the whether videos are available in episodes/clips ");
             assertionLogger.assertFalse(AutomationOperations.instance().navOp.shows.clipsEmpty());
    	}
    	
    	//Launch the video
    	AutomationOperations.instance().navOp.shows.launchVideo(0);
    	String clipTitle=AutomationOperations.instance().navOp.shows.getClipParentTitle();
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_Title_Subtitle_Season_Episode_Info_" + System.currentTimeMillis());
     	assertionLogger.setTestType("Test the whether show feed matches or not ");
        assertionLogger.assertEquals(showTitle,clipTitle);
        driverWrapper.back();
        driverWrapper.back();
    }
    
    //Episode is the default tab. Verify whether episode is appearing as default or clips. If no videos available in episode then clips is default.
    public void verifyDefaultTab(){
    	AutomationOperations.instance().navOp.shows.selectShow(0,0);
    	 assertionLogger.setTestType("Test the default tab");
    	if(!AutomationOperations.instance().navOp.shows.isEpisodeTab()){
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
    		AutomationOperations.instance().navOp.shows.selectEpisodesTab();
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.episodesEmpty());	
    	}else{
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isEpisodeTab());
    	}
    }
    
    
    /*
     * Verify vertical scroll functionality
     */
    public void verifyVerticalScroll(){
        //Move to top of the screen
        AutomationOperations.instance().navOp.shows.scrollHalfToTop();
        
        //Verify the Season/Episode/Clips are available at the correct locations or not
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "ShowDetails_CompleteScrolling_FadeIn_Image_" + System.currentTimeMillis());
        
        if(!AutomationOperations.instance().navOp.shows.isEpisodeTab()){
        	AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        }
        if(!AutomationOperations.instance().navOp.shows.episodesEmpty()){
        	AutomationOperations.instance().navOp.shows.scrollHalfToTop();
            driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "VerifyScreenScrolling_Episode_Tab_" + System.currentTimeMillis());
        }
        	
        if(!AutomationOperations.instance().navOp.shows.isClipsTab()){
           	AutomationOperations.instance().navOp.shows.selectClipsTab();
       }
            
        if(!AutomationOperations.instance().navOp.shows.clipsEmpty()){
        	AutomationOperations.instance().navOp.shows.scrollHalfToTop();
            driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "VerifyScreenScrolling_Clips_Tab_" + System.currentTimeMillis());
        }
    }
    
    
    
    /*
     * Verify More/Less functionalities
     */
    public void verifyMoreFunctionality(){
    	   //Verify MORE  functionality
        assertionLogger.setTestType("Test if MORE is working properly or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyContentFeature("MORE"));
        
        //Verify LESS functionality
        assertionLogger.setTestType("Test if LESS is working properly or not");
        assertionLogger.assertFalse(AutomationOperations.instance().navOp.shows.verifyContentFeature("LESS"));
        
        
        //Select Episode tab
        AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        //Swipe left to navigate to the other tab
        AutomationOperations.instance().navOp.shows.swipeLeft_Episode();
        //Verify for the clips tab
        assertionLogger.setTestType("Test swipe left is working or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
        
        //Swipe right to navigate to the other tab
        AutomationOperations.instance().navOp.shows.swipeRight_Clips();
        
        //Verify for the Episode tab
        assertionLogger.setTestType("Test swipe right is working or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isEpisodeTab());
        
        
        //Verify the MORE functionality to check the description is appearing after scrolling to the top
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyContentFeature("MORE"));
        AutomationOperations.instance().navOp.shows.scrollToBottom();
        
        
        //Verify context is displaying or it is in fade in state
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_More_Details_displaying_" + System.currentTimeMillis());
    }
    

   /*
    * Verify videos sort functionality
    */
    public void verifyEpisodesSort(){
    	AutomationOperations.instance().navOp.shows.selectShow(0,0);
    	
    	//Moving scroll to top
    	AutomationOperations.instance().navOp.shows.scrollToBottom();
    	
    	//Get the videos count
    	int videosCount=AutomationOperations.instance().navOp.shows.getVideosCount();
    	
    	//Getting the date information of the videos
    	List<String> videoDates=new ArrayList<String>();
    	
    	for(int count=0;count<videosCount;count++){
    		AutomationOperations.instance().navOp.shows.launchVideo(count);
    		if(AutomationOperations.instance().navOp.shows.isAirDateExists()){
    			videoDates.add(AutomationOperations.instance().navOp.shows.getAirDate());
    		}
    		driverWrapper.back();
    	}
    	
    	//Verify the sort order of the dates
    	assertionLogger.setTestType("Test the videos are stored in sorted or not");
    	for(int count=0;count<videoDates.size()-1;count++){
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyDate(videoDates.get(count),videoDates.get(count+1)));
    	}
    	
    	
    	
    }
    
    
    public void verifyRotateFunctionalities(){
    	boolean clipsTabStatus=false;
    	AutomationOperations.instance().navOp.shows.selectShow(0,0);
    	int seasonCount=AutomationOperations.instance().navOp.shows.getSeasonsCount();
    	
    	if(seasonCount>1){
    		AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
    	}
    	
    	//Get the season title
    	String seasonText=AutomationOperations.instance().navOp.shows.getSeasonTitle();
    	driverWrapper.rotate();
    	String updatedSeasonText=AutomationOperations.instance().navOp.shows.getSeasonTitle();
		driverWrapper.rotate();
    	assertionLogger.setTestType("Test the whether the season text is changing when rotate the device");
		assertionLogger.assertEquals(updatedSeasonText,seasonText);

		//Select the clips tab
		AutomationOperations.instance().navOp.shows.selectClipsTab();
    	assertionLogger.setTestType("Test the clips tab is selected or not");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
		if(AutomationOperations.instance().navOp.shows.clipsEmpty()){
			clipsTabStatus=true;
		}
		
		//rotate the device
		driverWrapper.rotate();
    	assertionLogger.setTestType("Test the clips tab is selected or not");
    	if(!clipsTabStatus){
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
    	}else{
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isEpisodeTab());
    	}
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_tabs_displays_in_paging_format_" + System.currentTimeMillis());
		driverWrapper.rotate();
		driverWrapper.back();
    }
    
    public void verifyShareFunctionality(){
    	AutomationOperations.instance().navOp.shows.selectShow(0,0);

        AutomationOperations.instance().navOp.mainToolbarShare();
        //Verify the installed apps in the device
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_shared_apps_visibility_" + System.currentTimeMillis());
        
    	//Verify the facebook app installed in the device
        assertionLogger.setTestType("Verify for the facebook availablity in the device");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.isFaceBookExists());

        //Select the facebook
		driverWrapper.getElementByName(ResourceLocator.device.AWE_SHARE_OPTIONS_FACEBOOK).click();
		
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_shared_items_and_shared_content_" + System.currentTimeMillis());
		//Verify user can cancel with out sharing
		driverWrapper.back();
		driverWrapper.back();
		driverWrapper.getElementByName(ResourceLocator.device.FACEBOOK_DISCARD_OPTION).click();
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "User_Navigates_back_from_Facebook_to_awe_" + System.currentTimeMillis());
        
        //Verify Share pop-up stays when it is tapped
        ((MobileElement)driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS)).tap(1, 10);
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_Share_pop-up_Visibility_" + System.currentTimeMillis());
        
        //Tap the out side and verify share pop-up dismissed
        driverWrapper.back();
       // ((MobileElement)driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_MORE_LINK)).tap(1, 10);
        assertionLogger.setTestType("Verify for share pop up is dismissed");
		assertionLogger.assertFalse(driverWrapper.elements(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).size()!=0);
        driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "Verify_Share_pop-up_dismisses_" + System.currentTimeMillis());
        
        //click on the more link
        driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS).click();
        assertionLogger.setTestType("Verify for share pop up is visible");
        assertionLogger.assertTrue(driverWrapper.elements(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).size()!=0);
        //Navigate to the back
        driverWrapper.back();
        assertionLogger.setTestType("Verify for share pop up is dismissed");
        assertionLogger.assertFalse(driverWrapper.elements(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).size()!=0);
    }
    

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}