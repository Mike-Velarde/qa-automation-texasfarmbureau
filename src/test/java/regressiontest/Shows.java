package regressiontest;

import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 01/04/2016.
 */
public class Shows extends AppiumMain {
	AssertionLogger assertionLogger = new AssertionLogger();

	@BeforeClass
	public void setup() {

	}

	@Test
	public void testShowDetails(){

		//Verify the shows displayed in the navigation drawer
		AutomationOperations.instance().navOp.openMainDrawerSafe();


		//Verify exists shows in main drawer
		assertionLogger.setTestType("Test has shows availble in the main drawer: ");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.shows.toString()));
		AutomationOperations.instance().navOp.closeMainDrawer();

		//Selct the shows
		AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);


		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_shows_feed_data_" + System.currentTimeMillis());

		//Verify shows arranged in alphabetical order
		assertionLogger.setTestType("Test has shows arranged in alphabetical order: ");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.hasShowsArrangedAlphabetically());
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_shows_arranged_alphabetically_" + System.currentTimeMillis());

		//Verify the last row of images cut off
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_last_row_images_cut_off_" + System.currentTimeMillis());

		//Verify user can scroll up
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_before_scroll_up_" + System.currentTimeMillis());
		AutomationOperations.instance().navOp.shows.scrollToBottom_Shows();
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_after_scroll_up_" + System.currentTimeMillis());
		driverWrapper.back();
		AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

		//Select the show
		AutomationOperations.instance().navOp.shows.selectShow(0,0);

		//Verify show details screen
		assertionLogger.setTestType("Test show detail screen : ");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasToolbarWatchlist());
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_before_running_background" + System.currentTimeMillis());

		//TODO:The run app in back ground is not working - app issue
		//Run app in background
/*		driverWrapper.runAppInBackground(10);
		assertionLogger.setTestType("Test after running in background verify the screen position : ");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasToolbarWatchlist());
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_after_running_background" + System.currentTimeMillis());
*/
		driverWrapper.back();




		//Verify placeholder displays
		assertionLogger.setTestType("Test verify brand and place holder displaying : ");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.getScreenTitle().equals(ResourceLocator.DrawerNavigationItem.shows.toString()));
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_brand_placeholder_displaying_" + System.currentTimeMillis());

		//Tap on multiple shows, at a time only one show has to display
		assertionLogger.setTestType("Test tap on multiple shows and ensure at a time only one show has to display : ");
		assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.selctMultipleShows());
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_one_show_displays_" + System.currentTimeMillis());



		//TODO: Define the logic to test on the phone, 7" tablet and 10" tablet 
		Dimension dimension=driverWrapper.manage().window().getSize();
		long width=dimension.getWidth();
		long height=dimension.getHeight();


		//Verify portrait screen
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_portrait_column_" + System.currentTimeMillis());
		assertionLogger.setTestType("Test the column count in portrait mode : ");
		if(width==720 && height==1280){
			assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.getShowsColumnCount(),1);
		}
		driverWrapper.rotate();
		driverWrapper.takeScreenshotSupressError(AutomationConfigProperties.screenshotsDirectory, "verify_landscape_column_" + System.currentTimeMillis());
		assertionLogger.setTestType("Test the column count in landscape mode: ");
		if(width==720 && height==1280){
			assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.getShowsColumnCount(),2);
		}
		driverWrapper.rotate();
	}

	@AfterClass
	public void tearDown(){
		assertionLogger.logMessages();
	}

}