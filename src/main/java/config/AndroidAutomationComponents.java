package config;

import operations.UserOperations;
import operations.UserOperationsAndroid;
import operations.navops.NavigationOperations;
import operations.navops.NavigationOperationsAndroid;

/**
 * Implementation of the strategy pattern, contains components of the system
 *
 * Created by ford.arnett on 10/29/15.
 */
public class AndroidAutomationComponents implements DeviceAutomationComponents {
    @Override
    public AutomationConfigurations getAutomationConfigurations() {
        return new AutomationConfigurationsAndroid();
    }

    @Override
    public NavigationOperations getNavigationOperations() {
        return new NavigationOperationsAndroid();
    }

    @Override
    public UserOperations getUserOperations() {
        return new UserOperationsAndroid();
    }

    @Override
    public ResourceLocator getResourceLocator() {
        return new ResourceLocatorAndroid();
    }
}
