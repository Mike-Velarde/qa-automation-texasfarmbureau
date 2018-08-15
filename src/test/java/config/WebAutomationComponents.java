package config;

import automationtests.assertions.AssertionLibrary;
import automationtests.assertions.AssertionLibraryWeb;
import com.bottlerocket.config.AutomationConfigurations;
import com.bottlerocket.config.AutomationConfigurationsWeb;
import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.webdriverwrapper.AppiumDriverWrapper;
import operations.UserOperations;
import operations.UserOperationsWeb;
import operations.navops.NavigationOperations;
import operations.navops.NavigationOperationsWeb;

/**
 * Created by ford.arnett on 8/10/18
 */
public class WebAutomationComponents implements DeviceAutomationComponents {
    @Override
    public AutomationConfigurations getAutomationConfigurations() {
        return new AutomationConfigurationsWeb();
    }

    @Override
    public NavigationOperations getNavigationOperations() {
        return new NavigationOperationsWeb();
    }

    @Override
    public UserOperations getUserOperations() {
        return new UserOperationsWeb();
    }

    @Override
    public ResourceLocator initResourceLocator() {
        return ResourceLocator.device = new ResourceLocatorWeb();
    }

    @Override
    public InputUtils createInputUtils(AppiumDriverWrapper driverWrapper) {
        return null;
    }

    @Override
    public AssertionLibrary getAssertions() {
        return new AssertionLibraryWeb();
    }


}
