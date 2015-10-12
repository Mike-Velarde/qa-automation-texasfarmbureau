package operations;


import config.AutomationConfigurations;
import operations.UserOperations;
import operations.navops.NavigationOperations;

/**
 * Head object for all operations that a test can use
 *
 * Created by ford.arnett on 9/3/15.
 */
public class AutomationOperations {
    private static volatile AutomationOperations instance;
    protected AutomationOperations(){}

    //Double check so we only synchronize when first created
    public static AutomationOperations instance(){
        if(instance == null){
            synchronized (AutomationOperations.class){
                if(instance == null){
                    instance = new AutomationOperations();
                }
            }
        }

        return instance;
    }

    public UserOperations userOp = new UserOperations();
    public NavigationOperations navOp = new NavigationOperations();
    public AutomationConfigurations config = new AutomationConfigurations();

}
