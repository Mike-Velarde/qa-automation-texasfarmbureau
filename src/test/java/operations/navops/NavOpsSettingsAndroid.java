package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import operations.AutomationOperations;

/**
 * Created by ford.arnett on 2/9/16.
 */
public class NavOpsSettingsAndroid extends NavOpsSettings {
    @Override
    public void backFromSettingsOption() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.mainToolbarBack();
    }
}
