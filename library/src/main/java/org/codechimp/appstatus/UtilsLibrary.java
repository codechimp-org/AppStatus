package org.codechimp.appstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.codechimp.appstatus.enums.UpdateFormat;
import org.codechimp.appstatus.objects.Status;

import java.net.MalformedURLException;
import java.net.URL;

class UtilsLibrary {

    static Status getLatestStatus(UpdateFormat updateFormat, String url) {
        if (updateFormat == UpdateFormat.XML){
            StatusParserXML parser = new StatusParserXML(url);
            return parser.parse();
        } else {
            return new StatusParserJSON(url).parse();
        }
    }

    static Boolean isNetworkAvailable(Context context) {
        Boolean res = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                res = networkInfo.isConnected();
            }
        }

        return res;
    }

    static Boolean isStringAnUrl(String s) {
        Boolean res = false;
        try {
            new URL(s);
            res = true;
        } catch (MalformedURLException ignored) {}

        return res;
    }
}
