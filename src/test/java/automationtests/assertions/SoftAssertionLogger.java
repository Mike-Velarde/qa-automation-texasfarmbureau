package automationtests.assertions;

import com.bottlerocket.utils.Logger;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ford.arnett on 1/17/17.
 */
public class SoftAssertionLogger extends SoftAssert {
    private String testNumber;
    private List<String> assertionMessages = new ArrayList<>();
    private String testType;

    @Override
    public void onBeforeAssert(IAssert assertion) {
        String assertionLog;

        if(assertion.getMessage() != null && !assertion.getMessage().isEmpty()) {
            setTestType(assertion.getMessage());
        }
        assertionLog = (test() + param1(assertion) + param2(assertion)); //+ errorMessage(assertion));

        assertionMessages.add(assertionLog);

    }

    @Override
    public void onAssertSuccess(IAssert assertion){
        int mostRecentIndex = assertionMessages.size() - 1;
        String message = assertionMessages.get(mostRecentIndex);
        //assertionMessages.set(mostRecentIndex, message);
        logMessage(message);

    }

    @Override
    public void onAssertFailure(IAssert assertion, AssertionError ex){
        int mostRecentIndex = assertionMessages.size() - 1;
        String message = assertionMessages.get(mostRecentIndex);
        message = message + errorMessage(assertion);
        //assertionMessages.set(mostRecentIndex, message);
        logMessage(message);
    }

    /**
     * Set a message to print after the test has run about what the point of the test was
     * @param s
     */
    public void setTestType(String s){
        testType = s;
        setTestType("Test number not set ", s);
    }

    public void setTestType(String testNumber, String testType) {
        this.testNumber = testNumber;
        this.testType = testType;
    }

    private String test(){
        String type;
        if(testNumber != null && !testNumber.equals("")){
            testNumber = "Test Number: " + testNumber + " ";
        }
        if(testType == null || testType.equals(""))
            type = "No test description is available for this test. ";
        else
            type = testNumber + testType;

        //reset testtype in case it is not set again before next test.
        testType = "";

        return " " + type;
    }

    private String param1(IAssert assertion){
        //don't want to show "expected" on single param cases
        if(assertion.getExpected() == null || assertion.getExpected().toString().equals("") || assertion.getExpected().toString().equals("true") || assertion.getExpected().toString().equals("false"))
            return " ";
        return " Expected Value: " + assertion.getExpected().toString();
    }

    private String param2(IAssert assertion) {
        //don't want to show null, empty, or true/false because these are almost always single param cases. May need to review if this causes unwanted behavior
        if(assertion.getExpected() == null || assertion.getExpected().toString().equals("") || assertion.getExpected().toString().equals("true") || assertion.getExpected().toString().equals("false"))
            return "";
        else{
            return " Actual Value: " + assertion.getActual().toString();
        }
    }

    private String errorMessage(IAssert assertion){
        if(assertion.getMessage() == null || assertion.getMessage().equals("")){
            return "";
        }
        else {
            return ", Error message: " + assertion.getMessage();
        }

    }

    public void addMessage(String message) {
        assertionMessages.add(message);
    }

    public void logMessages() {
        for(String s : assertionMessages){
            Logger.log(s);
        }
    }

    public void logMessage(String s) {
        Logger.log(s);
    }

    public void assertAll() {
        super.assertAll();
    }
}
