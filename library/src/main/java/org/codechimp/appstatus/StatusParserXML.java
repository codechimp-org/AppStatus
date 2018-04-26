package org.codechimp.appstatus;

import android.support.annotation.Nullable;
import android.util.Log;

import org.codechimp.appstatus.objects.Status;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class StatusParserXML {

    private static final String TAG = "AppStatus";

    private URL rssUrl;

    public StatusParserXML(String url) {
        try {
            this.rssUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public Status parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        InputStream inputStream = null;

        try {
            URLConnection connection = rssUrl.openConnection();
            inputStream = connection.getInputStream();
            SAXParser parser = factory.newSAXParser();
            StatusFileHandlerXML handler = new StatusFileHandlerXML();
            parser.parse(inputStream, handler);
            return handler.getUpdate();
        } catch (ParserConfigurationException | SAXException e) {
            Log.e(TAG, "The XML updater file is mal-formatted. AppStatus can't check for status.", e);
            return null;
        } catch (FileNotFoundException | UnknownHostException | ConnectException e) {
            Log.e(TAG, "The XML updater file is invalid or is down. AppStatus can't check for status.");
            return null;
        } catch (IOException e) {
            Log.e(TAG, "I/O error. AppStatus can't check for status.", e);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "The server is down or there isn't an active Internet connection.", e);
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing input stream", e);
                }
            }
        }

    }

}
