package smokeTest.generic;

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import operations.OperationsException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 11/11/15.
 */
public class Shows extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testShows() throws OperationsException {

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0,2);

        //For now I am making the assumption that if we can get the show title then we have made it to the show details screen
        assertionLogger.setTestType("Test if the show title is non empty");
        assertionLogger.assertNotEquals(AutomationOperations.instance().userOp.getShowDetailsShowTitle(), "");
        testShowDetailsDefaultTab();

        AutomationOperations.instance().navOp.addShowToWatchlist();

        AutomationOperations.instance().navOp.mainToolbarShare();
        AutomationOperations.instance().navOp.shareFacebook();

        String seasonNameBefore = AutomationOperations.instance().navOp.shows.getSeasonTitle();
        String seasonNameAfter = AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        assertionLogger.setTestType("Verify the season name has changed");
        assertionLogger.assertNotEquals(seasonNameBefore, seasonNameAfter);
        AutomationOperations.instance().navOp.shows.playFromActiveSeason(2);

        //Wait for ads
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AssertionLibrary.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);

        AutomationOperations.instance().navOp.mainToolbarBack();
        AutomationOperations.instance().navOp.mainToolbarBack();

        //This needs more work
        AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        //This isn't working right now
        AutomationOperations.instance().navOp.shows.scrollToBottom();

        AutomationOperations.instance().navOp.mainToolbarBack();

    }

    /**
     * TODO a change has seemed to make showDetailsEpisodesOrClipsSelected no longer work, need to investigate
     *
     * Verify that the correct tab on the show details screen has been defaulted to by checking the criteria for clips.
     * If the criteria for clips has not been met, then it should be episodes
     */
    private void testShowDetailsDefaultTab() {

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

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}