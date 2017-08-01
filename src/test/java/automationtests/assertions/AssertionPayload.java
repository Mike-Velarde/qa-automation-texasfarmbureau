package automationtests.assertions;

/**
 * Methods can return this to give more data in reporting rather than just being true or false
 *
 * Probably move into framework?
 *
 * Created by ford.arnett on 3/28/17.
 */
public class AssertionPayload {
    public boolean assertSuccessful;
    //If this needs to be an object, will need to override toString and add support for whatever objects are needed so message can be reported properly
    public String payload;
    public String failureMessage;
    public String successMessage;
    public boolean takeScreenshotFailure = true;
    public String screenShotFailFilename = "";
    public boolean takeScreenshotSuccess = false;
    public String screenShotSuccessFilename = "";

    public AssertionPayload(boolean assertSuccessful, String successMessage, String failureMessage) {
        this.assertSuccessful = assertSuccessful;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
    }

    public AssertionPayload(boolean assertSuccessful, String successMessage, String failureMessage, String screenShotFailFilename) {
        this.assertSuccessful = assertSuccessful;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.screenShotFailFilename = screenShotFailFilename;
    }

    public AssertionPayload(boolean assertSuccessful, String successMessage, String failureMessage, boolean takeScreenshotFailure) {
        this.assertSuccessful = assertSuccessful;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.takeScreenshotFailure = takeScreenshotFailure;
    }

    public AssertionPayload(boolean assertSuccessful, String successMessage, String failureMessage, String screenShotSuccessFilename, String screenShotFailFilename) {
        this.assertSuccessful = assertSuccessful;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.screenShotFailFilename = screenShotFailFilename;
        this.screenShotSuccessFilename = screenShotSuccessFilename;
        this.takeScreenshotSuccess = true;
    }
}
