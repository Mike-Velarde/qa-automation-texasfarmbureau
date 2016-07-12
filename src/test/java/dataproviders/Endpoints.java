package dataproviders;

import com.bottlerocket.config.AutomationConfigurations;
import config.ResourceLocator;
import org.testng.annotations.DataProvider;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class Endpoints {
    @DataProvider(name = "settings-endpoints")
    public static Object[][] endpoints() {
        if (AutomationConfigurations.isAndroid()) {
            return new Object[][]{
                    {"Check endpoint content", ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE_ID, ResourceLocator.device.USA_ENDPOINT_ABOUT_USA_NOW},
                    {"Check endpoint content", ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE_ID, ResourceLocator.device.USA_ENDPOINT_FAQ},
                    {"Check endpoint content", ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE_ID, ResourceLocator.device.USA_ENDPOINT_PRIVACY_POLICY},
                    {"Check endpoint content", ResourceLocator.device.AWE_SETTINGS_TERMS_AND_CONDITIONS_TITLE_ID, ResourceLocator.device.USA_ENDPOINT_TERMS_AND_CONDITIONS},
                    {"Check endpoint content", ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE_ID, ResourceLocator.device.BR_ENDPOINT_ABOUT_BR}
            };
        }

        if (AutomationConfigurations.isIos()) {
            return new Object[][]{
                    {"Check endpoint content", "About", ResourceLocator.device.USA_ENDPOINT_ABOUT_USA_NOW},
                    {"Check endpoint content", "FAQ", ResourceLocator.device.USA_ENDPOINT_FAQ},
                    {"Check endpoint content", "Privacy Policy", ResourceLocator.device.USA_ENDPOINT_PRIVACY_POLICY},
                    {"Check endpoint content", "Terms & Conditions", ResourceLocator.device.USA_ENDPOINT_TERMS_AND_CONDITIONS},
                    {"Check endpoint content", "About Bottle Rocket and AWE", ResourceLocator.device.BR_ENDPOINT_ABOUT_BR}
            };
        }

        return null;
    }
}
