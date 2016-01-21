package testngreporting;

import config.AutomationConfigProperties;
import org.testng.reporters.SuiteHTMLReporter;

/**
 * Created by ford.arnett on 12/21/15.
 */
public class CustomSuiteHTMLReporter extends SuiteHTMLReporter {
    @Override
    protected String generateOutputDirectoryName(String outputDirectory){
        return AutomationConfigProperties.testNGOutputDirectory;
    }
}
