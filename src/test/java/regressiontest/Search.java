package regressiontest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 04/06/2016.
 */
public class Search extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testSearchFunctionalities() {

        // Select the shows
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.mainToolbarSearch();

        // Verify search input spans full screen
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Search_02_Verify_Search_input_field_spans_full_screen_" + System.currentTimeMillis());

        // Verify keyboard visibility
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Search_03_Verify_keyboard_visibility_" + System.currentTimeMillis());

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Search_09_Verify_search_input_location_after_brand_logo_" + System.currentTimeMillis());

        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Navigates to the About BR screen
        assertionLogger.setTestType("Test for Shows title : ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.mainToolbarSearch();

        AutomationOperations.instance().navOp.serachBarBack();

        // Navigates to the About BR screen
        assertionLogger.setTestType("Test for Shows title : ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.mainToolbarSearch();

        driverWrapper.back();

        // Navigates to the About BR screen
        assertionLogger.setTestType("Test for Shows title : ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.mainToolbarSearch();

        AutomationOperations.instance().userOp.enterSearchTitle("testing");

        assertionLogger.setTestType("Test for the search content is entered : ");
        assertionLogger.assertEquals(AutomationOperations.instance().userOp.getSearchFieldText(), "testing");

        // Clear the text in the search field
        AutomationOperations.instance().userOp.clearSearchField();

        assertionLogger.setTestType("Test for the search content is entered : ");
        assertionLogger.assertEquals(AutomationOperations.instance().userOp.getSearchFieldText(), "");
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}