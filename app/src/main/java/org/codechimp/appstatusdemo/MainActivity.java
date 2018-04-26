package org.codechimp.appstatusdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.codechimp.appstatus.AppStatus;
import org.codechimp.appstatus.enums.AppStatusError;
import org.codechimp.appstatus.enums.UpdateFormat;
import org.codechimp.appstatus.objects.Status;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppStatus";

    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainView = findViewById(R.id.main_layout_id);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppStatus appStatus = new AppStatus(this)
                .setUpdateFormat(UpdateFormat.JSON)
                .setUpdateURL("https://raw.githubusercontent.com/codechimp-org/AppStatus/master/samplestatus.json")
//                .setUpdateFormat(UpdateFormat.XML)
//                .setUpdateURL("https://raw.githubusercontent.com/codechimp-org/AppStatus/master/samplestatus.xml")
                .withListener(new AppStatus.StatusListener() {
                    @Override
                    public void onSuccess(Status status) {
                        showSnackbar(mainView, status);
                    }

                    @Override
                    public void onFailed(AppStatusError error) {
                        // Do your error handling here, could be incorrect URL, bad json or xml, or network issue
                    }
                });

        appStatus.start();
    }

    void showSnackbar(View view, Status status) {
        final Snackbar snackbar = Snackbar.make(view, status.getMessage(), Snackbar.LENGTH_INDEFINITE);

        // Styling for the action button
        snackbar.setActionTextColor(Color.WHITE);

        // Styling for rest of text
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        // Styling for background
        if (status.getPriority().equalsIgnoreCase("HIGH"))
            snackbarView.setBackgroundColor(Color.RED);

        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
