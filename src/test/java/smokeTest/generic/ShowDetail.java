package smokeTest.generic;

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
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by ford.arnett on 11/11/15.
 */
public class ShowDetail extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testShowDetails(){

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
       
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
        
        
        //Share in the facebook
        AutomationOperations.instance().navOp.mainToolbarShare();
        AutomationOperations.instance().navOp.shareFacebook();

        assertionLogger.logMessages();
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
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.isRemoveShowWatchListExists());

        
        //Verify watch list status
        getScreenshot("Verify_'-'_symbol_to_ensure_removeShowWatchList_");
        
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
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.isAddShowWatchListExists());

        //Removing the show from watch list
        getScreenshot("Verify_'+'_symbol_to_ensure_addShowWatchList_");
        
        driverWrapper.back();
        
        int removedCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        //Verify whether the show added to the watch list or not
        assertionLogger.setTestType("Test the show added to the watch list or not");
        assertionLogger.assertEquals(watchCount,removedCount+1);
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
        getScreenshot("Verify_AWE_Place_Holder_Image");
        
        AutomationOperations.instance().navOp.shows.selectShow(0,0);
        
        //Move to the half of the screen
        AutomationOperations.instance().navOp.shows.scrollToHalf();
        
        //Verify the background color fade in or not also verify the Snipe, Show title, Subtitle, MORE link, Sponser Image fade to black 
        getScreenshot("ShowDetails_HalfScrolling_FadeIn_Image_");
        
        //Verify the scroll bottom functionalities in episode and clips tab
        verifyVerticalScroll();
        
        //Navigate to the back and verify the shows screen
        assertionLogger.setTestType("Test whther user able to navigate to back or not");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.navigateToShows(),"Shows");
        
        
        //Verify for the sponser logo
        getScreenshot("Verify_Sponser_Logo_");
    }
    
    /*
     * Verifies the respective tab has respective assets or not
     */
    public void verifyTabAssets(){
        //Verify Episode tab contains all the episodes only
        assertionLogger.setTestType("Test Episode tab contains all episodes or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyEpisodeAssets());
        
        //Verify the episode tab contains all the episodes or not
        getScreenshot("ShowDetails_Episode_Assets_Image_");
        
        //Verify Clips tab contains all the clips only
        assertionLogger.setTestType("Test Clips tab contains all clips or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyClipAssets());

        //Verify the clip tab contains all the clips or not
        getScreenshot("ShowDetails_Clip_Assets_Image_");

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
    	getScreenshot("Verify_Title_Subtitle_Season_Episode_Info_");
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
        getScreenshot("ShowDetails_CompleteScrolling_FadeIn_Image_");
        
        if(!AutomationOperations.instance().navOp.shows.isEpisodeTab()){
        	AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        }
        if(!AutomationOperations.instance().navOp.shows.episodesEmpty()){
        	AutomationOperations.instance().navOp.shows.scrollHalfToTop();
        	getScreenshot("VerifyScreenScrolling_Episode_Tab_");
        }
        	
        if(!AutomationOperations.instance().navOp.shows.isClipsTab()){
           	AutomationOperations.instance().navOp.shows.selectClipsTab();
       }
            
        if(!AutomationOperations.instance().navOp.shows.clipsEmpty()){
        	AutomationOperations.instance().navOp.shows.scrollHalfToTop();
        	getScreenshot("VerifyScreenScrolling_Clips_Tab_");
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
        getScreenshot("Verify_More_Details_displaying_");
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
    	}
    	
    	//Verify the sort order of the dates
    	assertionLogger.setTestType("Test the videos are stored in sorted or not");
    	for(int count=0;count<videoDates.size()-1;count++){
    		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyDate(videoDates.get(count),videoDates.get(count+1)));
    	}
    	
    	
    	
    }
    
    
    /*
     * Gets the screenshot
     */
    public void getScreenshot(String discription){
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, discription + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
    	
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}