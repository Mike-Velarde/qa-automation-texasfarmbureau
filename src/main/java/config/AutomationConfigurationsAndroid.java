package config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class AutomationConfigurationsAndroid extends AutomationConfigurations {

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    @Override
    public void loadConfigVariables(){
        //load common config before loading Android specific
        super.loadConfigVariables();


        //Android specific configs go here
        /**
         * Capabilities
         */
        AutomationConfigProperties.appPath = automationProperties.getProperty("APK_LOCATION");
    }

    @Override
    public DesiredCapabilities setCapabilities(){
        //Set common capabilities
        DesiredCapabilities capabilities = super.setCapabilities();

        //Android specific capabilities go here


        return capabilities;
    }
}
