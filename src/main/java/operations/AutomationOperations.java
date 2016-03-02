package operations;


import com.bottlerocket.config.AutomationConfigurations;
import config.DeviceAutomationComponents;
import operations.navops.NavigationOperations;

/**
 * Head object for all operations that a test can use
 *
 * Created by ford.arnett on 9/3/15.
 */
public class AutomationOperations {
    public DeviceAutomationComponents deviceAutomationComponents;

    private AutomationOperations(){}

    private static class SingletonHelper{
        private static final AutomationOperations INSTANCE = new AutomationOperations();
    }

    public static AutomationOperations instance(){
        return SingletonHelper.INSTANCE;
    }

    public UserOperations userOp;
    public NavigationOperations navOp;
    public AutomationConfigurations config;

    public void initOperations(DeviceAutomationComponents deviceAutomationComponents){
        this.deviceAutomationComponents = deviceAutomationComponents;

        deviceAutomationComponents.initResourceLocator();
        config = deviceAutomationComponents.getAutomationConfigurations();
        navOp = deviceAutomationComponents.getNavigationOperations();
        userOp = deviceAutomationComponents.getUserOperations();

    }
}
