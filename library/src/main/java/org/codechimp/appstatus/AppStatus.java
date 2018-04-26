package org.codechimp.appstatus;

import android.content.Context;
import android.support.annotation.NonNull;

import org.codechimp.appstatus.enums.AppStatusError;
import org.codechimp.appstatus.enums.UpdateFormat;
import org.codechimp.appstatus.objects.Status;

public class AppStatus {
    private Context context;
    private StatusListener appStatusListener;
    private UpdateFormat updateFormat;
    private String updateUrl;
    private UtilsAsync.LatestStatus latestStatus;

    public interface StatusListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't retrieve the latest status
         *
         * @param status object with the latest status information
         * @see org.codechimp.appstatus.objects.Status
         */
        void onSuccess(Status status);

        void onFailed(AppStatusError error);
    }


    public AppStatus(Context context) {
        this.context = context;
        this.updateFormat = UpdateFormat.JSON;
    }

    /**
     * Set the source where the latest update can be found. Default: JSON.
     *
     * @param updateFormat source where the latest update is uploaded.
     * @return this
     * @see UpdateFormat
     * @see <a href="https://github.com/codechimp-org/AppStatus/wiki">Additional documentation</a>
     */
    public AppStatus setUpdateFormat(UpdateFormat updateFormat) {
        this.updateFormat = updateFormat;
        return this;
    }


    /**
     * Set the url to the latest status info.
     *
     * @param updateUrl file
     * @return this
     */
    public AppStatus setUpdateURL(@NonNull String updateUrl) {
        this.updateUrl = updateUrl;
        return this;
    }

    /**
     * Method to set the StatusListener for the AppStatusUtils actions
     *
     * @param appStatusListener the listener to be notified
     * @return this
     * @see StatusListener
     */
    public AppStatus withListener(StatusListener appStatusListener) {
        this.appStatusListener = appStatusListener;
        return this;
    }

    /**
     * Execute AppUpdaterUtils in background.
     */
    public void start() {
        latestStatus = new UtilsAsync.LatestStatus(context, updateFormat, updateUrl, new StatusListener() {
            @Override
            public void onSuccess(Status status) {
                if (appStatusListener != null) {
                    appStatusListener.onSuccess(status);
                } else {
                    throw new RuntimeException("You must provide a listener for the AppUpdaterUtils");
                }
            }

            @Override
            public void onFailed(AppStatusError error) {
                if (appStatusListener != null) {
                    appStatusListener.onFailed(error);
                } else {
                    throw new RuntimeException("You must provide a listener for the AppUpdaterUtils");
                }
            }
        });

        latestStatus.execute();
    }

    /**
     * Stops the execution of AppStatus.
     */
    public void stop() {
        if (latestStatus != null && !latestStatus.isCancelled()) {
            latestStatus.cancel(true);
        }
    }
}
