package operations;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;

/**
 *
 * Created by ford.arnett on 9/3/15.
 */
public class UserOperationsWeb extends UserOperations {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }
    
}
