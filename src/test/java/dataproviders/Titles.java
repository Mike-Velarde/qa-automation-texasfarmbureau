package dataproviders;

import config.ResourceLocator;
import org.testng.annotations.DataProvider;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class Titles {
    @DataProvider(name = "settings-verify-titles")
    public static Object[][] dataProviderMethod() {
        return new Object[][] {
                { "Check titles",ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE_ID, "About Brand"},
                { "Check titles",ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE_ID , "FAQ"},
                { "Check titles",ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE_ID , "Privacy Policy"},
                { "Check titles",ResourceLocator.device.AWE_SETTINGS_TERMS_AND_CONDITIONS_TITLE_ID , "Terms & Conditions"},
                { "Check titles",ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE_ID , "About Bottle Rocket and AWE"},
                { "Check titles",ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID , "Developer Options"}
        };
    }
}
