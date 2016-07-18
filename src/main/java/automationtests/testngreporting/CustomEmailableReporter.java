package automationtests.testngreporting;

import org.testng.ISuite;
import org.testng.reporters.EmailableReporter2;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * This is probably the wrong way to do this, but this seemed like the most sensible hook to move the report files after they are created.
 * Ideally we don't have to move them, we can just overwrite where they go, but when I tried doing this in {@link CustomSuiteHTMLReporter} it didn't
 *
 * Created by ford.arnett on 12/23/15.
 */
public class CustomEmailableReporter extends EmailableReporter2 {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,String outputDirectory){

    }
}
