package config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ford.arnett on 10/7/15.
 */
public class LoadConfig {
    public void loadConfig() throws Exception{
        String result;
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(AppDefaults.configFileName);
        if(inputStream != null){
            properties.load(inputStream);
        }
        else{
            throw new FileNotFoundException("Properties file " + properties);
        }
    }
}
