package config;

import automationtests.assertions.AssertionLibrary;
import com.bottlerocket.config.AutomationConfigurations;
import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.driverwrapper.AppiumDriverWrapper;
import com.bottlerocket.driverwrapper.DriverWrapper;
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

    InputUtils createInputUtils(AppiumDriverWrapper driverWrapper);

    AssertionLibrary getAssertions();
}
