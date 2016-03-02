package dataproviders;

import com.bottlerocket.config.AutomationConfigurations;
import config.ResourceLocator;
import org.testng.annotations.DataProvider;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class Titles {
    @DataProvider(name = "settings-verify-titles")
    public static Object[][] dataProviderMethod() {
        if(AutomationConfigurations.isAndroid()) {
            return new Object[][]{
                    {"Check titles", ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE_ID, "About Brand"},
                    {"Check titles", ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE_ID, "FAQ"},
                    {"Check titles", ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE_ID, "Privacy Policy"},
                    {"Check titles", ResourceLocator.device.AWE_SETTINGS_TERMS_AND_CONDITIONS_TITLE_ID, "Terms & Conditions"},
                    {"Check titles", ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE_ID, "About Bottle Rocket and AWE"},
                    {"Check titles", ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID, "Developer Options"}
            };
        }
        else if (AutomationConfigurations.isIos()){
            return new Object[][]{
                    {"Check titles", "FAQ", "Settings"},
                    {"Check titles", "Privacy Policy", "Settings"},
                    {"Check titles", "Terms & Conditions", "Settings"},
                    {"Check titles", "About Bottle Rocket", "Settings"},
                    {"Check titles", "Feedback", "Settings"},
                    {"Check titles", "About", "Settings"}
            };
        }
        else {
            com.bottlerocket.utils.ErrorHandler.printErr(new Exception("OS not recognized"));
            return null;
        }
    }
}
