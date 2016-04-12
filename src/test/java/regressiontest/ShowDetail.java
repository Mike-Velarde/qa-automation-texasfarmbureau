package regressiontest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;

import assertions.AssertionLogger;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 03/18/2016.
 */
public class ShowDetail extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    /**
     * Test the basic features
     */
    // @Test
    public void test01_BasicFeatures() {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

        // Verify the the portrait mode screen appears as mentioned in the confluence
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_02_Verify_portrait_mode_matches_confluence_" + System.currentTimeMillis());

        // Navigate to the back
        driverWrapper.rotate();

        // Verify the the landscape mode screen appears as mentioned in the confluence
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_01_Verify_landscape_mode_matches_confluence_" + System.currentTimeMillis());

        driverWrapper.rotate();
    }

    /*
     * Verifies the watch list functionality
     */
    // @Test
    public void test02_WatchList() {
        // Watch list tests
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        // Remove from the watch list
        AutomationOperations.instance().navOp.removeFromWatchList();

        driverWrapper.back();
        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();

        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Adding to the watch list
        AutomationOperations.instance().navOp.tapAddShowToWatchlist();

        // Verify watch list status
        assertionLogger.setTestType("Test if remove watch list is appearing");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasRemoveShowWatchList());

        // Verify watch list status
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_52_Verify_'-'_symbol_to_ensure_removeShowWatchList_" + System.currentTimeMillis());

        driverWrapper.back();

        int addedCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        // Verify whether the show added to the watch list or not
        assertionLogger.setTestType("Test the show added to the watch list or not");
        assertionLogger.assertEquals(watchCount + 1, addedCount);

        watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Remove the show from watchlist
        AutomationOperations.instance().navOp.tapRemoveShowToWatchlist();

        // Verify watch list status
        assertionLogger.setTestType("Test if add watch list is appearing");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasAddShowWatchList());

        // Removing the show from watch list
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_51_Verify_'+'_symbol_to_ensure_addShowWatchList_" + System.currentTimeMillis());

        driverWrapper.back();

        int removedCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        // Verify whether the show added to the watch list or not
        assertionLogger.setTestType("Test the show added to the watch list or not");
        assertionLogger.assertEquals(watchCount, removedCount + 1);

        // Rotate the device
        driverWrapper.rotate();
        int rotateCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        driverWrapper.rotate();
        assertionLogger.setTestType("Test the watch list count will remain same when rotate the device");
        assertionLogger.assertEquals(removedCount, rotateCount);

    }

    /**
     * TODO a change has seemed to make showDetailsEpisodesOrClipsSelected no longer work, need to investigate
     *
     * Verify that the correct tab on the show details screen has been defaulted to by checking the criteria for clips. If the criteria for clips has not been met, then it should be episodes
     */
    // @Test
    private void test03_ShowDetailsDefaultTab() {
        // Select the show
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        boolean episodesOrClips = AutomationOperations.instance().navOp.shows.showDetailsEpisodesOrClipsSelected();

        // Find out what if episodes and/or clips are empty
        AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        boolean episodesEmpty = AutomationOperations.instance().navOp.shows.episodesEmpty();
        AutomationOperations.instance().navOp.shows.selectClipsTab();
        boolean clipsEmpty = AutomationOperations.instance().navOp.shows.clipsEmpty();

        // If it defaults to clips, make sure that episodes are empty and clips
        // are not
        if (!episodesOrClips) {
            assertionLogger.assertTrue(episodesEmpty && !clipsEmpty);
            AutomationOperations.instance().navOp.shows.selectClipsTab();
        }
        // If it defaults to episodes, make sure the criteria (episodes empty,
        // clips not) for defaulting to clips is not met
        else {
            assertionLogger.assertFalse(episodesEmpty && !clipsEmpty);
            AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        }
    }

    /*
     * Verifies the scrolling functionality
     */
    // @Test
    public void test04_ScrollFunctionality() {
        // Verify the AWE place holder image
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_24_Verify_AWE_Place_Holder_Image_" + System.currentTimeMillis());

        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        
        String image1=driverWrapper.takeObjectScreenshot(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE), AutomationConfigProperties.screenshotsDirectory,"AAFR_Show_Details_10_beforeScrollUp_"+ System.currentTimeMillis());
        // Move to the half of the screen
        AutomationOperations.instance().navOp.shows.scrollToHalf();

        String image2=driverWrapper.takeObjectScreenshot(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE), AutomationConfigProperties.screenshotsDirectory,"AAFR_Show_Details_10_afterScrollUp_"+ System.currentTimeMillis());
        
        // Verify Episode tab contains all the episodes only
        assertionLogger.setTestType("Test the images are identical or not: ");
        assertionLogger.assertFalse(driverWrapper.doesImagesIdentical(image1,image2));

        // Show title, Subtitle, MORE link, Sponsor Image fade to black 
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_49_Verify_activity_circle_while_loading_Videos_" + System.currentTimeMillis()); 

        // Verify the background color fade in or not also verify the Snipe, 
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_10_ShowDetails_HalfScrolling_FadeIn_Image_" + System.currentTimeMillis());

        // Verify the scroll bottom functionalities in episode and clips tab 
        verifyVerticalScroll();

        // Navigate to the back and verify the shows screen 
        driverWrapper.back(); 
        assertionLogger.setTestType("Test whther user able to navigate to back or not"); 
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Verify for the sponsor logo 
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_13_Verify_Sponser_Logo_" + System.currentTimeMillis());
    }

    /*
     * Verifies the respective tab has respective assets or not
     */
    // @Test
    public void test05_TabAssets() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Verify Episode tab contains all the episodes only
        assertionLogger.setTestType("Test Episode tab contains all episodes or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyEpisodeAssets());

        // Verify the episode tab contains all the episodes or not
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_14_ShowDetails_Episode_Assets_Image_" + System.currentTimeMillis());

        // Verify Clips tab contains all the clips only
        assertionLogger.setTestType("Test Clips tab contains all clips or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyClipAssets());

        // Verify the clip tab contains all the clips or not
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_14_ShowDetails_Clip_Assets_Image_" + System.currentTimeMillis());

    }

    /*
     * Verifies the respective season videos are displaying or not
     */
    // @Test
    public void test06_Seasons() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        assertionLogger.setTestType("Test the Episodes are displaying as per the season");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifySeasonEpisodesDisplay());
        assertionLogger.setTestType("Test the Clips are displaying as per the season");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifySeasonClipsDisplay());
    }

    /*
     * Verifies the show feed information on the videos
     */
    // @Test
    public void test07_ShowsFeed() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        String showTitle = AutomationOperations.instance().userOp.getShowDetailsShowTitle();
        AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        if (AutomationOperations.instance().navOp.shows.episodesEmpty()) {
            AutomationOperations.instance().navOp.shows.selectClipsTab();
            assertionLogger.setTestType("Test the whether videos are available in episodes/clips ");
            assertionLogger.assertFalse(AutomationOperations.instance().navOp.shows.clipsEmpty());
        }

        // Launch the video
        AutomationOperations.instance().navOp.shows.launchVideoDetail(0);
        String clipTitle = AutomationOperations.instance().navOp.shows.getClipParentTitle();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_44_Verify_Title_Subtitle_Season_Episode_Info_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test the whether show feed matches or not ");
        assertionLogger.assertEquals(showTitle, clipTitle);
        driverWrapper.back();
        driverWrapper.back();
    }

    // Episode is the default tab. Verify whether episode is appearing as default or clips. If no videos available in episode then clips is default.
    // @Test
    public void test08_DefaultTab() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        assertionLogger.setTestType("Test the default tab");
        if (!AutomationOperations.instance().navOp.shows.isEpisodeTab()) {
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
            AutomationOperations.instance().navOp.shows.selectEpisodesTab();
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.episodesEmpty());
        } else {
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isEpisodeTab());
        }
    }

    /*
     * Verify vertical scroll functionality
     */
    private void verifyVerticalScroll() {
        // Move to top of the screen
        AutomationOperations.instance().navOp.shows.scrollHalfToTop();

        // Verify the Season/Episode/Clips are available at the correct locations or not
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_08_ShowDetails_CompleteScrolling_FadeIn_Image_" + System.currentTimeMillis());

        if (!AutomationOperations.instance().navOp.shows.isEpisodeTab()) {
            AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        }
        if (!AutomationOperations.instance().navOp.shows.episodesEmpty()) {
            AutomationOperations.instance().navOp.shows.scrollHalfToTop();
            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_09_VerifyScreenScrolling_Episode_Tab_" + System.currentTimeMillis());
        }

        if (!AutomationOperations.instance().navOp.shows.isClipsTab()) {
            AutomationOperations.instance().navOp.shows.selectClipsTab();
        }

        if (!AutomationOperations.instance().navOp.shows.clipsEmpty()) {
            AutomationOperations.instance().navOp.shows.scrollHalfToTop();
            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_09_VerifyScreenScrolling_Clips_Tab_" + System.currentTimeMillis());
        }
    }

    /*
     * Verify More/Less functionalities
     */
    // @Test
    public void test09_MoreFunctionality() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        // Verify MORE functionality
        assertionLogger.setTestType("Test if MORE is working properly or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyContentFeature("MORE"));

        // Verify LESS functionality
        assertionLogger.setTestType("Test if LESS is working properly or not");
        assertionLogger.assertFalse(AutomationOperations.instance().navOp.shows.verifyContentFeature("LESS"));

        // Scroll up side
        AutomationOperations.instance().navOp.shows.scrollToHalf();
        // Select Episode tab
        AutomationOperations.instance().navOp.shows.selectEpisodesTab();
        // Swipe left to navigate to the other tab
        AutomationOperations.instance().navOp.shows.swipeLeft_Episode();
        // Verify for the clips tab
        assertionLogger.setTestType("Test swipe left is working or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());

        // Swipe right to navigate to the other tab
        AutomationOperations.instance().navOp.shows.swipeRight_Clips();

        driverWrapper.back();
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Verify for the Episode tab
        assertionLogger.setTestType("Test swipe right is working or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isEpisodeTab());

        // Verify the MORE functionality to check the description is appearing
        // after scrolling to the top
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyContentFeature("MORE"));
        AutomationOperations.instance().navOp.shows.scrollToBottom();

        // Verify context is displaying or it is in fade in state
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_33_Verify_More_Details_displaying_" + System.currentTimeMillis());
    }

    /*
     * Verify videos sort functionality
     */
    // @Test
    public void test10_EpisodesSort() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Moving scroll to top
        AutomationOperations.instance().navOp.shows.scrollToBottom();

        // Get the videos count
        int videosCount = AutomationOperations.instance().navOp.shows.getVideosCount();

        // Getting the date information of the videos
        List<String> videoDates = new ArrayList<String>();

        for (int count = 0; count < videosCount; count++) {
            AutomationOperations.instance().navOp.shows.launchVideoDetail(count);
            if (AutomationOperations.instance().navOp.shows.doesAirDateExists()) {
                videoDates.add(AutomationOperations.instance().navOp.shows.getVideoDetailsAirDate());
            }
            driverWrapper.back();
        }

        // Verify the sort order of the dates
        assertionLogger.setTestType("Test the videos are stored in sorted or not");
        for (int count = 0; count < videoDates.size() - 1; count++) {
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.verifyDateAfter(videoDates.get(count), videoDates.get(count + 1)));
        }
    }

    @Test
    public void test11_RotateFunctionalities() {
        boolean clipsTabStatus = false;
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        int seasonCount = AutomationOperations.instance().navOp.shows.getSeasonsCount();

        if (seasonCount > 1) {
            AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        }

        // Get the season title
        String seasonText = AutomationOperations.instance().navOp.shows.getSeasonTitle();
        driverWrapper.rotate();
        String updatedSeasonText = AutomationOperations.instance().navOp.shows.getSeasonTitle();
        driverWrapper.rotate();
        assertionLogger.setTestType("Test the whether the season text is changing when rotate the device");
        assertionLogger.assertEquals(updatedSeasonText, seasonText);

        // Select the clips tab
        AutomationOperations.instance().navOp.shows.selectClipsTab();
        assertionLogger.setTestType("Test the clips tab is selected or not");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
        if (AutomationOperations.instance().navOp.shows.clipsEmpty()) {
            clipsTabStatus = true;
        }

        // rotate the device
        driverWrapper.rotate();
        assertionLogger.setTestType("Test the clips tab is selected or not");
        if (!clipsTabStatus) {
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isClipsTab());
        } else {
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.isEpisodeTab());
        }
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_48_Verify_tabs_displays_in_paging_format_" + System.currentTimeMillis());
        driverWrapper.rotate();
        driverWrapper.back();
    }

    // @Test
    public void test12_ShareFunctionality() {
        if (AutomationOperations.instance().navOp.shows.hasMore()) {
            driverWrapper.back();
        }
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        AutomationOperations.instance().navOp.mainToolbarShare();
        // Verify the installed apps in the device
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_56_Verify_shared_apps_visibility_" + System.currentTimeMillis());

        // Verify the facebook app installed in the device
        assertionLogger.setTestType("Verify for the facebook availablity in the device");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.isFaceBookExists());

        // Select the facebook
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SHARE_OPTIONS_FACEBOOK).click();

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_57_Verify_shared_items_and_shared_content_" + System.currentTimeMillis());
        // Verify user can cancel with out sharing
        driverWrapper.back();
        driverWrapper.back();
        driverWrapper.getElementByName(ResourceLocator.device.FACEBOOK_DISCARD_OPTION).click();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_58_User_Navigates_back_from_Facebook_to_awe_" + System.currentTimeMillis());

        // Verify Share pop-up stays when it is tapped
        ((MobileElement) driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS)).tap(1, 10);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_61_Verify_Share_pop-up_Visibility_" + System.currentTimeMillis());

        // Tap the out side and verify share pop-up dismissed
        driverWrapper.back();
        assertionLogger.setTestType("Verify for share pop up is dismissed");
        assertionLogger.assertFalse(driverWrapper.elements(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).size() != 0);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Show_Details_62_Verify_Share_pop-up_dismisses_" + System.currentTimeMillis());

        // click on the more link
        driverWrapper.getElementByName(ResourceLocator.device.AWE_MAIN_TOOLBAR_MORE_OPTIONS).click();
        assertionLogger.setTestType("Verify for share pop up is visible");
        assertionLogger.assertTrue(driverWrapper.elements(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).size() != 0);
        // Navigate to the back
        driverWrapper.back();
        assertionLogger.setTestType("Verify for share pop up is dismissed");
        assertionLogger.assertFalse(driverWrapper.elements(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_SHARE_OVERFLOW)).size() != 0);
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }
}