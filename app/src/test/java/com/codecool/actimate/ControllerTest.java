package com.codecool.actimate;

import com.codecool.actimate.controller.APIController;
import com.codecool.actimate.controller.TextValidator;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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

    @Test
    public void requestBuilderTest() throws Exception {
        String content = "\"age\":\"21\"";
        Request requestExample = APIController.requestBuilder("http://example.com", content);
        assertEquals(content, getBodyAsString(requestExample.body()));
    }

    @Test
    public void isEmailValidTest() throws Exception {
        assertTrue(TextValidator.isEmailValid("a@g.c"));
    }

    @Test
    public void isEmailValidAtMissingTest() throws Exception {
        assertFalse(TextValidator.isEmailValid("ag.c"));
    }

    @Test
    public void isEmailValidDotMissingTest() throws Exception {
        assertFalse(TextValidator.isEmailValid("a@gc"));
    }

    @Test
    public void isPasswordValidTest() throws Exception {
        assertTrue(TextValidator.isPasswordValid("Codec00l"));
    }

    @Test
    public void isPasswordValidTooShortTest() throws Exception {
        assertFalse(TextValidator.isPasswordValid("Code"));
    }

    @Test
    public void isPasswordValidMissingCapitalLetterTest() throws Exception {
        assertFalse(TextValidator.isPasswordValid("codec00l"));
    }

    @Test
    public void isPasswordValidMissingSmallLetterTest() throws Exception {
        assertFalse(TextValidator.isPasswordValid("CODEC00L"));
    }

    @Test
    public void isPasswordValidMissingNumberTest() throws Exception {
        assertFalse(TextValidator.isPasswordValid("CODEC00L"));
    }

    private String getBodyAsString(RequestBody body) {
        try {
            final Buffer sink = new Buffer();
            final MediaType mediaType = body.contentType();
            final Charset charset = getCharset(mediaType);
            body.writeTo(sink);
            return sink.readString(charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Charset getCharset(MediaType mediaType) {
        if (mediaType != null) {
            return mediaType.charset(Charset.defaultCharset());
        }

        return Charset.defaultCharset();
    }

}
