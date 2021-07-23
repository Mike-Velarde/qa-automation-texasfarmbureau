package config;

import com.bottlerocket.errorhandling.ConfigurationException;
import domod.User;

/**
 * This should be the object that holds your data for various test runs.
 * It should setup whatever data you need for the particular run, switching envs, users and other data you need for test runs.
 * Try using the constructor to give whatever locale/env/other data that would change what data you use. Then use a few ifs/switches to setup the data that you need.
 * The goal should be that your test main should create a new object, and you are not changing this class from run to run.
 *
 * For example in your TestMain you'd have something like:
 * TestDataManager manager = new TestDataManager(DEV_ENV, "US");
 * Then TestDataManager would handle account setups for tests ran in dev in America.
 *
 * See RH for an active example where this is working. I am leaving some example code in the template to illustrate the idea of this class
 *
 * Created by ford.arnett on 1/27/21
 */
public class TestDataManager {

    public static final String DEV_ENV = "dev";
    public static final String US_LOCALE = "US";

    protected User exampleUser = new User("user", "password");

    public TestDataManager (String envType, String locale) throws ConfigurationException {}

    private String setEnvVariable(String envType) throws ConfigurationException {return null;}

    public void setUser(String env, String locale) {}

    public void setEnvCapabilities(String env, String locale) {}

    private void setNormalEnv(String locale) {}

    private void setClientEnv(String locale) {}

}
