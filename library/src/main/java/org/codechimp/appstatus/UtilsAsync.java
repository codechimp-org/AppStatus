package org.codechimp.appstatus;

import android.content.Context;
import android.os.AsyncTask;

import org.codechimp.appstatus.enums.AppStatusError;
import org.codechimp.appstatus.enums.UpdateFormat;
import org.codechimp.appstatus.objects.Status;

import java.lang.ref.WeakReference;

class UtilsAsync {

    static class LatestStatus extends AsyncTask<Void, Void, Status> {
        private WeakReference<Context> contextRef;
        private UpdateFormat updateFormat;
        private String url;
        private AppStatus.StatusListener listener;

        public LatestStatus(Context context, UpdateFormat updateFormat, String url, AppStatus.StatusListener listener) {
            this.contextRef = new WeakReference<>(context);
            this.updateFormat = updateFormat;
            this.url = url;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Context context = contextRef.get();
            if (context == null || listener == null) {
                cancel(true);
            } else if (UtilsLibrary.isNetworkAvailable(context)) {
                if (url == null || !UtilsLibrary.isStringAnUrl(url)) {
                    listener.onFailed(AppStatusError.URL_MALFORMED);
                    cancel(true);
                }
            } else {
                listener.onFailed(AppStatusError.NETWORK_NOT_AVAILABLE);
                cancel(true);
            }
        }

        @Override
        protected org.codechimp.appstatus.objects.Status doInBackground(Void... voids) {
            try {
                org.codechimp.appstatus.objects.Status status = UtilsLibrary.getLatestStatus(updateFormat, url);
                if (status != null) {
                    return status;
                } else {
                    AppStatusError error = updateFormat == UpdateFormat.XML ? AppStatusError.XML_ERROR
                            : AppStatusError.JSON_ERROR;

                    if (listener != null) {
                        listener.onFailed(error);
                    }
                    cancel(true);
                    return null;
                }
            } catch (Exception ex) {
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPostExecute(org.codechimp.appstatus.objects.Status status) {
            super.onPostExecute(status);

            if (listener != null) {
                listener.onSuccess(status);
            }
        }
    }

}
