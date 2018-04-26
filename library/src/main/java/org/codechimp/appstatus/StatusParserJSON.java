package org.codechimp.appstatus;

import android.util.Log;

import org.codechimp.appstatus.objects.Status;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

class StatusParserJSON {
    private URL jsonUrl;

    private static final String KEY_MESSAGE = "message";
    private static final String KEY_PRIORITY = "priority";

    public StatusParserJSON(String url) {
        try {
            this.jsonUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public Status parse(){

        try {
            JSONObject json = readJsonFromUrl();
            Status status = new Status();
            status.setMessage(json.getString(KEY_MESSAGE).trim());
            status.setPriority(json.getString(KEY_PRIORITY).trim());
            return status;
        } catch (IOException e) {
            Log.e("AppStatus", "The server is down or there isn't an active Internet connection.", e);
        } catch (JSONException e) {
            Log.e("AppStatus", "The JSON status file is mal-formatted. AppStatus can't check for status.");
        }

        return null;
    }


    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl() throws IOException, JSONException {
        InputStream is = this.jsonUrl.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

}
