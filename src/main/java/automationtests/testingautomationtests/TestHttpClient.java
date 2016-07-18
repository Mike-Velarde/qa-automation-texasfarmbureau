package automationtests.testingautomationtests;

import com.bottlerocket.httphandler.HttpClient;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ford.arnett on 2/12/16.
 */
public class TestHttpClient {

    public static void main(String[] args) throws IOException{
        HttpClient testHttpClient = new HttpClient();

        QdobaApiController controller = new QdobaApiController();
        //testHttpClient.initHttpsClientSpec();
        controller.loginQdoba(testHttpClient);

        String downloadedURL = testHttpClient.downloadUrl(new URL("https://www.google.com"));
        //System.out.println(downloadedURL);

        //String response = testHttpClient.get("/articles?include=author");
        //System.out.println(response);
    }


    public static class QdobaApiController {
        public void loginQdoba(HttpClient client) throws IOException {
            String url = "https://stage.qdoba.bottlerocketservices.com/api/v1/security/token";
            //String url = "http://stage.qdoba.bottlerocketservices.com:443/api/v1/security/reset/questions/default";
            //String url = "https://stage.qdoba.bottlerocketservices.com:443/api/v1/security/token;";
            //String url = "http://app-stage-2-0-1558814161.us-west-2.elb.amazonaws.com:443/api/v1/security/token";
            String response = client.post(url , "{\"username\": \"bob.burrito@gmail.com\",\"password\": \"burrito\"}");

            System.out.println(response);
        }
    }
}
