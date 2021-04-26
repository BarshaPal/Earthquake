package com.example.earthquake;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Word>> {
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private WordAdapter mAdapter;
    TextView mEmptyStateTextView;
    ConnectivityManager cm;
    /**
     * URL to query the USGS dataset for earthquake information
     */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(1, null, this);
        Log.e("Init Loder","intit Loder called");
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
         mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
            mAdapter = new WordAdapter(this, new ArrayList<Word>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word earthquake_caurrent=mAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(earthquake_caurrent.getMurl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                Log.v("URI2",earthquake_caurrent.getMurl());
                startActivity(websiteIntent);
            }
        });

    }


    @Override
    public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.e("Create Loder","Create Loder called");
        return  new EarthquakeLoader(this,USGS_REQUEST_URL);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> earthquakes) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_earthquakes);
        mAdapter.clear();
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            mEmptyStateTextView.setText("No Earthquakes Found!");

        } else {

            mEmptyStateTextView.setText("No Internet Connection!");

        }
        Log.e("Finish Loder","Finish Loder called");
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
}
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        Log.e("Reset Loder","Reset Loder called");
        mAdapter.clear();
    }
}



