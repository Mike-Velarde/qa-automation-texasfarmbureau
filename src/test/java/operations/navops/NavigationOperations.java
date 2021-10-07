package operations.navops;


import automationtestinstance.AutomationTestManager;
import operations.TestInitializerListener;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class NavigationOperations implements TestInitializerListener {
    AutomationTestManager am;

    //NavOps classes here

    @Override
    public void init(AutomationTestManager am) {
        this.am = am;

        //init nav classes here

    }

}

