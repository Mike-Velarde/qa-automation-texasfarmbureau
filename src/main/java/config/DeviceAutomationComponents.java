package config;

import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.webdriver.WebDriverWrapper;
import operations.UserOperations;
import operations.navops.NavigationOperations;

/**
 * Created by ford.arnett on 10/29/15.
 */
public interface DeviceAutomationComponents {
    AutomationConfigurations getAutomationConfigurations();

    NavigationOperations getNavigationOperations();

    UserOperations getUserOperations();

    ResourceLocator initResourceLocator();

    InputUtils createInputUtils(WebDriverWrapper driverWrapper);
}
