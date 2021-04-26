package com.example.earthquake;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<Word>> {

        /** Tag for log messages */
        private static final String LOG_TAG = EarthquakeLoader.class.getName();

        /** Query URL */
        private String mUrl;


        public EarthquakeLoader(Context context, String url) {
                super(context);
                mUrl = url;
        }

        @Override
        protected void onStartLoading() {
                Log.e("Start Loder","Start Loder called");
                forceLoad();
        }

        /**
         * This is on a background thread.
         */
        @Override
        public List<Word> loadInBackground() {
                if (mUrl == null) {
                        Log.e("Background Loder","Null Background Loder called");
                        return null;
                }

                // Perform the network request, parse the response, and extract a list of earthquakes.
                List<Word> earthquakes = QueryUtils.extractEarthquakes(mUrl);
                return earthquakes;
        }
}