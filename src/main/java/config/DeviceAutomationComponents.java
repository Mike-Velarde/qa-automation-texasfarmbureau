package config;

import operations.UserOperations;
import operations.navops.NavigationOperations;

/**
 * Created by ford.arnett on 10/29/15.
 */
public interface DeviceAutomationComponents {
    AutomationConfigurations getAutomationConfigurations();

    NavigationOperations getNavigationOperations();

    UserOperations getUserOperations();

    ResourceLocator getResourceLocator();
}
