package operations;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import domod.UserBank;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class UserOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }
    
}
