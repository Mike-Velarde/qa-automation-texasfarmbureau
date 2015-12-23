package testngreporting;

import config.AppDefaults;
import org.testng.reporters.XMLReporter;

/**
 * Created by ford.arnett on 12/23/15.
 */
public class CustomXMLReporter extends XMLReporter {

    @Override
    public String getOutputDirectory() {
        return AppDefaults.testNGOutputDirectory;
    }
}
