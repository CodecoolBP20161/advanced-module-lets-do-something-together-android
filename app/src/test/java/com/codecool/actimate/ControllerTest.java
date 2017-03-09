package com.codecool.actimate;

import com.codecool.actimate.controller.APIController;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ControllerTest {

    @Test
    public void createJsonTest() throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("email", "milu_laci@codecool.com");
        data.put("password", "Codec00l");
        JSONObject json = APIController.createJson(data);

        assertTrue(json.get("email").equals("milu_laci@codecool.com") &&
                    json.get("password").equals("Codec00l"));

    }

    @Test
    public void createManyElementJsonTest() throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("fname", "Laci");
        data.put("lname", "Milu");
        data.put("age", "21");
        data.put("gender", "male");
        data.put("nationality", "hungarian");
        JSONObject json = APIController.createJson(data);

        assertTrue(json.get("fname").equals("Laci") &&
                    json.get("lname").equals("Milu") &&
                    json.get("age").equals("21") &&
                    json.get("gender").equals("male") &&
                    json.get("nationality").equals("hungarian"));

    }
}
