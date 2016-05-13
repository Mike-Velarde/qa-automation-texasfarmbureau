package regressiontest;

import operations.OperationsException;
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
    public void testSearchFunctionalities() throws OperationsException {

        // Select the shows
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

        // Click on the search icon
        AutomationOperations.instance().navOp.mainToolbarSearch();

        // Verify search input spans full screen
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Search_02_verify_search_input_field_spans_full_screen_" + System.currentTimeMillis());

        // Verify keyboard visibility
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Search_03_verify_keyboard_visibility_" + System.currentTimeMillis());

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Search_09_verify_search_input_location_after_brand_logo_" + System.currentTimeMillis());

        // Select the show
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Verify the screen title has shows
        assertionLogger.setTestType("Test for Shows title : ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Click on the search icon
        AutomationOperations.instance().navOp.mainToolbarSearch();

        // Click on the search bar back button
        AutomationOperations.instance().navOp.searchBarBack();

        // Verify the screen title as shows
        assertionLogger.setTestType("Test for Shows title : ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Click on the search icon
        AutomationOperations.instance().navOp.mainToolbarSearch();

        driverWrapper.back();

        // Verify the screen title as shows
        assertionLogger.setTestType("Test for Shows title : ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Click on the search icon
        AutomationOperations.instance().navOp.mainToolbarSearch();

        // Enter the text "testing" in the search bar
        AutomationOperations.instance().userOp.enterSearchTitle("testing");

        // Verify the "testing" is entered in the search bar
        assertionLogger.setTestType("Test for the search content is entered : ");
        assertionLogger.assertEquals(AutomationOperations.instance().userOp.getSearchFieldText(), "testing");

        // Clear the text in the search field
        AutomationOperations.instance().userOp.clearSearchField();

        // Clear the text in the search field
        assertionLogger.setTestType("Test for the search content is entered : ");
        assertionLogger.assertEquals(AutomationOperations.instance().userOp.getSearchFieldText(), "");
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}
