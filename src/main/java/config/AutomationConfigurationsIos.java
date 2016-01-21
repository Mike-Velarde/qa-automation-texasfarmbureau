package config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class AutomationConfigurationsIos extends AutomationConfigurations {

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    @Override
    public void loadConfigVariables(){
        //load common config before loading ios specific
        super.loadConfigVariables();

        //Android specific configs go here
        /**
         * Capabilities
         */
        AutomationConfigProperties.appPath = automationProperties.getProperty("IPA_LOCATION");
        AutomationConfigProperties.buildNumber = automationProperties.getProperty("UUID");

    }

    @Override
    public DesiredCapabilities setCapabilities(){
        //Set common capabilities
        DesiredCapabilities capabilities = super.setCapabilities();

        //Ios specific capabilities go here


        return capabilities;
    }
}
