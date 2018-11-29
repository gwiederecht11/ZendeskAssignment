/**
 * Created by gwiederecht on 11/27/18.
 */
import junit.framework.Assert;
import org.junit.Test;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientTest {

    @Test
    public void checkGetFeatureFlag(Client c) throws JSONException{

        //Make sure properly gets the flag and gets the correct value. Success Response.
        String flag_1_response = c.getFeatureFlag("flag_1");
        JSONObject flag_1_object = new JSONObject(flag_1_response);
        Assert.assertNotNull(flag_1_object);
        Assert.assertTrue(flag_1_object.getBoolean("success"));
        int flag_1_val = flag_1_object.getInt("value");
        Assert.assertEquals(flag_1_val, 1);

        //Make sure we get an error trying to get a flag that doesn't exist. Error Response.
        String flag_error_response = c.getFeatureFlag("flag_error");
        JSONObject flag_error_object = new JSONObject(flag_error_response);
        Assert.assertNotNull(flag_error_object);
        Assert.assertFalse(flag_error_object.getBoolean("success"));
    }

    @Test
    public void checkSetFeatureFlag(Client c) throws JSONException{
        String response_1 = c.setFeatureFlag("flag_0", 0);
        String response_2 = c.setFeatureFlag("flag_1", 1);

        //Check responses are success
        JSONObject flag_1_object = new JSONObject(response_1);
        JSONObject flag_2_object = new JSONObject(response_2);
        Assert.assertTrue(flag_1_object.getBoolean("success"));
        Assert.assertTrue(flag_2_object.getBoolean("success"));

        //Check get the correct value when call getFeatureFlag
        String get_response_1 = c.getFeatureFlag("flag_0");
        String get_response_2 = c.getFeatureFlag("flag_1");

        //Check responses are success
        flag_1_object = new JSONObject(get_response_1);
        flag_2_object = new JSONObject(get_response_2);
        Assert.assertTrue(flag_1_object.getBoolean("success"));
        Assert.assertTrue(flag_2_object.getBoolean("success"));
        int flag_1_val = flag_1_object.getInt("value");
        int flag_2_val = flag_2_object.getInt("value");
        Assert.assertEquals(flag_1_val, 0);
        Assert.assertEquals(flag_2_val, 1);
    }

    //Check that we get an error if we try to set a flag that doesn't exist, or setting it an incorrect value.
    public void checkSetFeatureFlagInvalidValue(Client c) throws JSONException{
        String response_1 = c.setFeatureFlag("flag_error", 0);
        String response_2 = c.setFeatureFlag("flag_error", 2);

        JSONObject flag_1_object = new JSONObject(response_1);
        JSONObject flag_2_object = new JSONObject(response_2);

        Assert.assertFalse(flag_1_object.getBoolean("success"));
        Assert.assertFalse(flag_2_object.getBoolean("success"));
    }


    public static void main(String[] args) throws JSONException{

        Client c = new Client();
        ClientTest test = new ClientTest();

        //Create 5 feature flags
        for (int i = 1; i < 6; i++) {
            String flag_name = "flag_" + String.valueOf(i);
            c.createFeatureFlag(flag_name);
        }
        test.checkGetFeatureFlag(c);
        test.checkSetFeatureFlag(c);
        test.checkSetFeatureFlagInvalidValue(c);
        System.out.println("All tests pass!");
    }
}
