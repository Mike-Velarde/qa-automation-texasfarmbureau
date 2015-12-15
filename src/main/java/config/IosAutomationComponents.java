package config;

import operations.UserOperations;
import operations.navops.NavigationOperations;
import operations.navops.NavigationOperationsIos;
import operations.UserOperationsIos;

/**
 * Implementation of the strategy pattern, contains components of the system
 *
 * Created by ford.arnett on 10/29/15.
 */
public class IosAutomationComponents implements DeviceAutomationComponents {
    @Override
    public AutomationConfigurations getAutomationConfigurations() {
        return new AutomationConfigurationsIos();
    }

    @Override
    public NavigationOperations getNavigationOperations() {
        return new NavigationOperationsIos();
    }

    @Override
    public UserOperations getUserOperations() {
        return new UserOperationsIos();
    }

    @Override
    public ResourceLocator getResourceLocator() {
        return new ResourceLocatorIos();
    }
}
