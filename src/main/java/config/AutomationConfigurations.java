package config;



import com.bottlerocket.utils.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ford.arnett on 10/9/15.
 */
public abstract class AutomationConfigurations {

    /**
     * Load properties from the properties file
     * @return
     * @throws Exception
     */
    public static Properties loadConfig() throws Exception{
        Properties properties = new Properties();

        InputStream inputStream = AutomationConfigurations.class.getClassLoader().getResourceAsStream(AppDefaults.CONFIG_FILE_NAME);
        if(inputStream != null){
            properties.load(inputStream);
        }
        else{
            throw new FileNotFoundException("Properties file " + properties);
        }

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

    protected boolean getAsBoolean(Properties prop, String property){
        String booleanProp = prop.getProperty(property);
        if(booleanProp == null)
            return AppDefaults.noReset;
        return Boolean.parseBoolean(booleanProp);
    }

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    public abstract void loadConfigVariables();

    public abstract DesiredCapabilities setCapabilities();

}
