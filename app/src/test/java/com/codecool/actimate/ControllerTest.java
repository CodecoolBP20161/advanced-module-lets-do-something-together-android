package com.codecool.actimate;

import com.codecool.actimate.controller.APIController;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ControllerTest {

    @Test
    public void createJsonTest() throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("email", "milu_laci@codecool.com");
        data.put("password", "Codec00l");
        System.out.println(data.toString()+ " HashMap");

        JSONObject json = new JSONObject();
        System.out.println(json);
//        "\"email\": \"milu_laci@codecool.com\", \"password\": \"Codec00l\""
        json.put("email", "milu_laci@codecool.com");
        json.put("password", "Codec00l");
        System.out.println(json);

        assertEquals("\"email\": \"milu_laci@codecool.com\", \"password\": \"Codec00l\"", APIController.createJson(data));
    }
}
