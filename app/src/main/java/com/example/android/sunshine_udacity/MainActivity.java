package com.example.android.sunshine_udacity;

import androidx.appcompat.app.AppCompatActivity;

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
    // Create a field to store the weather display TextView
    TextView mWeatherDisplayListView;

    //Add a TextView variable for the error message display

    TextView errorMessageTextView;

    //Add a ProgressBar variable to show and hide the progress bar
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Use findViewById to get a reference to the weather display TextView
        mWeatherDisplayListView = (TextView)findViewById(R.id.tv_weather_data);


             errorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

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
        mWeatherDisplayListView.setVisibility(View.VISIBLE);

    }

    //Create a method that will hide the weather data and show the error message


    private void showErrorMessage(){
        mWeatherDisplayListView.setVisibility(View.INVISIBLE);
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
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                for (String weatherString : weatherData) {
                    mWeatherDisplayListView.append((weatherString) + "\n\n\n");
                }
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

            mWeatherDisplayListView.setText("");
            // Call loadWeatherData when the search menu item is clicked
            loadWeatherData();
            return true;
        }

        return super.onContextItemSelected(item);

    }

}
