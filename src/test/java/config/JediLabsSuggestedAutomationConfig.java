package config;

import java.io.InputStream;

/**
 * This is code copied from Adam Newman given in Jedi Labs. I am documenting this here so it is not lost, and can be implemented later.
 * The idea is that most of the parsing lives in the automation framework, while there is an "extraparsing" element which lives in each specific app as necessary
 *
 * Created by ford.arnett on 2/3/16.
 */
public class JediLabsSuggestedAutomationConfig {

    public interface ExtraParser<T> {
        T parseExtra(InputStream extraStream);
    }

/*    AutomationConfiguration {
        ...
        public <T> T parseExtra(ExtraParser<T> extraParser) {
            //Load Extra String from config file or whatever
            try {
                InputStream extraStream = loadTheStuffFromWherever();
                extraParser.parseExtra(extraStream);
            } catch (IOException e) {
                //handle this
            } finally {
                extraStream.close();
            }
        }*/

        //Maybe heterogeneous map for storage http://stevewedig.com/2014/08/28/type-safe-heterogenous-containers-in-java/
    public void saveExtra(Object extraData) {
       // mExtraObject = extraData;
    }

    public <T> T getExtra(Class <T> clazz) {
        //return class.cast(mExtraObject); //Something like this
        return null;
    }

//This goes in the specific project
//--------------Test App Code-----------

/*        MyTestThingStuffEtc {

        init() {

        ...
        MyExtraModel extraModel = mAutomationConfiguration.parseExtra(new MyExtraParser());


        mAutomationConfiguration.getExtra(MyExtraModel.class).foo;

        }

        }*/

public class MyExtraModel {
    String foo;
    String bar;
    String baz;
}

public class MyExtraParser implements JediLabsSuggestedAutomationConfig.ExtraParser<MyExtraModel> {
    public MyExtraModel parseExtra(InputStream extraStream) {
        //GSON or something
        MyExtraModel result = new MyExtraModel();
        //result.foo = //whatever
        return result;
    }
}
}
