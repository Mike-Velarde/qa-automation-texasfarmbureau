package operations.navops;


import com.bottlerocket.driverwrapper.DriverWrapper;
import operations.AutomationOperationsListener;


/**
 * Handles navigation between screens
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class NavigationOperations implements AutomationOperationsListener {
    DriverWrapper driverWrapper;

    //NavOps classes here

    @Override
    public void init(DriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;

        //init nav classes here

    }

}

