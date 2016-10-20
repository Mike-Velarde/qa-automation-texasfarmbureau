package automationtests.assertions;

import com.bottlerocket.utils.Logger;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import java.util.ArrayList;
import java.util.List;

/**
 * This may need to be moved outside of the automation library. It's dangerous to have the library depend on testng
 *
 * Created by ford.arnett on 11/20/15.
 */
public class AssertionLogger extends Assertion {
    private List<String> assertionMessages = new ArrayList<>();
    private String testType;

    @Override
    public void onBeforeAssert(IAssert assertion) {
        String assertionLog;

        assertionLog = (test() + param1(assertion) + param2(assertion)); //+ errorMessage(assertion));

        assertionMessages.add(assertionLog);
    }

    @Override
    public void onAssertSuccess(IAssert assertion){
        int mostRecentIndex = assertionMessages.size() - 1;
        String message = assertionMessages.get(mostRecentIndex);
        message = "Passed " + message;
        assertionMessages.set(mostRecentIndex, message);
    }

    @Override
    public void onAssertFailure(IAssert assertion, AssertionError ex){
        int mostRecentIndex = assertionMessages.size() - 1;
        String message = assertionMessages.get(mostRecentIndex);
        message = "Failed " + message + errorMessage(assertion);
        assertionMessages.set(mostRecentIndex, message);
    }

    /**
     * Set a message to print after the test has run about what the point of the test was
     * @param s
     */
    public void setTestType(String s){
        testType = s;
    }

    private String test(){
        String type;
        if(testType == null)
            type = "";
        else
          type = testType;

        //reset testtype in case it is not set again before next test.
        testType = "";

        return "Test: " + type;
    }

    private String param1(IAssert assertion){
        return " " +assertion.getActual().toString();
    }

    private String param2(IAssert assertion) {
        //don't want to show null, empty, or true/false because these are almost always single param cases. May need to review if this causes unwanted behavior
        if(assertion.getExpected() == null || assertion.getExpected().toString().equals("") || assertion.getExpected().toString().equals("true") || assertion.getExpected().toString().equals("false") )
            return "";
        else{
            return ", " + assertion.getExpected();
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

}
