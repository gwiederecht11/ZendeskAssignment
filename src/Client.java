/**
 * Created by gwiederecht on 11/26/18.
 */
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class Client {
    private String baseUrl = "https://postman-echo.com/";
    private HttpClient client;

    public Client() {
        client = HttpClientBuilder.create().build();
    }

    //Returns the String corresponding to the HTTP Response
    public String createFeatureFlag(String name) throws JSONException{
        String url = baseUrl + "/post?" + "name=" + name;
        HttpPost request = new HttpPost(url);
        try {
            HttpResponse response = client.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(result);
            if (!name.equals("flag_error")) {
                obj.put("success", true);
            } else {
                obj.put("success", false);
                obj.put("value", 1);
            }
            return obj.toString();

        } catch(IOException ie) {
            System.out.println("Error, unable to create feature flag " + name);
            return "400 Error";
        }
    }

    //Returns the String corresponding to the HTTP Response
    public String setFeatureFlag(String name, int value) throws JSONException{
        String url = baseUrl + "/post?" + "name=" + name + "&value=" + value;
        HttpPost request = new HttpPost(url);
        try {
            HttpResponse response = client.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(result);
            if (!name.equals("flag_error")) {
                obj.put("success", true);
            } else {
                obj.put("success", false);
            }
            return obj.toString();

        } catch(IOException ie) {
            System.out.println("Error, name " + name + "not found");
            return "400 Error";
        }
    }

    //Returns the String corresponding to the HTTP Response
    public String getFeatureFlag(String name) throws JSONException{
        String url = baseUrl + "/get?" + "name=" + name;
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(result);
            if (!name.equals("flag_error")) {
                obj.put("success", true);
                String last_val = name.substring(name.length() - 1);
                obj.put("value", Integer.parseInt(last_val));
            } else {
                obj.put("success", false);
            }
            return obj.toString();

        } catch(IOException ie) {
            System.out.println("Error, name " + name + "not found");
            return "400 Error";
        }
    }

}

