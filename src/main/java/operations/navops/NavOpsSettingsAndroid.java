package operations.navops;

import operations.AutomationOperations;

/**
 * Created by ford.arnett on 2/9/16.
 */
public class NavOpsSettingsAndroid extends NavOpsSettings {
    @Override
    public void backFromSettingsOption() {
        AutomationOperations.instance().navOp.mainToolbarBack();
    }
}
