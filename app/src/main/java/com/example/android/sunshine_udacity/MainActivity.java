package com.example.android.sunshine_udacity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
    // completed Add a class file called ForecastAdapter
    // completed Extend RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>

    // completed Create a private string array called mWeatherData

    // completed Create the default constructor (we will pass in parameters in a later lesson)

    // completed Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // cmpleted Extend RecyclerView.ViewHolder

    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////
    // completed Create a public final TextView variable called mWeatherTextView

    // completed Create a constructor for this class that accepts a View as a parameter
    // completed Call super(view) within the constructor for ForecastAdapterViewHolder
    // completed Using , get a reference to this layout's TextView and save it to mWeatherTextView
    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////


    // completed Override onCreateViewHolder
    // completed Within onCreateViewHolder, inflate the list item xml into a view
    // completed Within onCreateViewHolder, return a new ForecastAdapterViewHolder with the above view passed in as a parameter

    // completed (27) Override onBindViewHolder
    // completed Set the text of the TextView to the weather for this list item's position

    // completed Override getItemCount
    // completed Return 0 if mWeatherData is null, or the size of mWeatherData if it is not null

    // completed Create a setWeatherData method that saves the weatherData to mWeatherData
    // completed After you save mWeatherData, call notifyDataSetChanged
    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////




    private RecyclerView mRecyclerView;

    private ForecastAdapter mForecastAdapter;


    //Add a TextView variable for the error message display

    TextView errorMessageTextView;

    //Add a ProgressBar variable to show and hide the progress bar
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

            mRecyclerView = (RecyclerView)  findViewById(R.id.recyclerview_forecast);


             errorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);




        // TODO (40) Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size

        // TODO (41) set mForecastAdapter equal to a new ForecastAdapter

        // TODO (42) Use mRecyclerView.setAdapter and pass in mForecastAdapter

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new ForecastAdapter();
        mRecyclerView.setAdapter(mForecastAdapter);


            loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        //Call loadWeatherData to perform the network request to get the weather
        loadWeatherData();
    }

    // Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData
    private void loadWeatherData() {
        showWeatherDataView();
        String weather = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(weather);
    }

    //Create a method called showWeatherDataView that will hide the error message and show the weather data

    private void showWeatherDataView(){
        errorMessageTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    //Create a method that will hide the weather data and show the error message


    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }

    //Create a class that extends AsyncTask to perform network requests
    private class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            loadingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }


            // Create URL object

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);



            // Perform HTTP request to the URL and receive a JSON response back
            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         *
         */
        @Override
        protected void onPostExecute(String[] weatherData) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                showWeatherDataView();
                // COMPLETED (45) Instead of iterating through every string, use mForecastAdapter.setWeatherData and pass in the weather data
                mForecastAdapter.setmWeatherData(weatherData);
            } else {
                showErrorMessage();
            }
        }
    }

    //Display or Menu; See Menus form Android Developer console
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forecast, menu);
        return true;
    }

    //Handle menu action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemThatWasSelected = item.getItemId();

        if  (menuItemThatWasSelected == R.id.action_search){

            mForecastAdapter.setmWeatherData(null);

            // Call loadWeatherData when the search menu item is clicked
            loadWeatherData();
            return true;
        }

        return super.onContextItemSelected(item);

    }

}
