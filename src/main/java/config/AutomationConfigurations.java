package config;



import com.bottlerocket.utils.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * It may not be necessary for loadConfigVariables and setCapabilities to be abstract, this needs to be reevaluated.
 * It seems like the subclasses may not have enough difference to need different methods, especially when the configurations themselves are passed in.
 *
 * Created by ford.arnett on 10/9/15.
 */
public abstract class AutomationConfigurations {
    protected String platformSpecificConfigFile;

    /**
     * Load properties from the properties file
     * @return
     * @throws Exception
     */
    public static Properties loadConfig() throws Exception{
        Properties properties = new Properties();

        //This will break if the folder structure changes. May want to improve this to make it more flexible
        InputStream inputStream = new FileInputStream("src/main/resources/appconfig.properties");
        properties.load(inputStream);

        return properties;
    }

    protected int getIntSafe(String s, int defaultValue){
        try{
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    protected boolean getAsBoolean(Properties prop, String property, boolean defaultValue){
        String booleanProp = prop.getProperty(property);
        if(booleanProp == null)
            return defaultValue;
        return Boolean.parseBoolean(booleanProp);
    }

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    public abstract void loadConfigVariables();

    public abstract DesiredCapabilities setCapabilities();

}
