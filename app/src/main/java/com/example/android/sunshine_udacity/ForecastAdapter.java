package com.example.android.sunshine_udacity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

private String [] mWeatherData;

//Create the default constructor
public ForecastAdapter (){


}

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ForecastAdapterViewHolder viewHolder = new ForecastAdapterViewHolder(view);

        //Set the text of viewHolderIndex
        //viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

        // ColorUtils.getViewHolderBackgroundColorFromInstance and pass in a Context and the viewHolderCount
        //int backgroundColorForViewHolder = ColorUtils.getViewHolderBackgroundColorFromInstance(context, viewHolderCount);

        // Set the background color of viewHolder.itemView with the color from above
        // viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        // Increment viewHolderCount and log its value
        // viewHolderCount = viewHolderCount + 1;

        //Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
        // + viewHolderCount);


        return viewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */


    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        String weatherForThisDay = mWeatherData[position];
        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mWeatherData) return 0;
        return mWeatherData.length;
    }


    public void setmWeatherData(String[] weatherData) {
        this.mWeatherData = weatherData;
        notifyDataSetChanged();
    }



   public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
    public final TextView mWeatherTextView;

    public ForecastAdapterViewHolder (View view){
        super (view);
        mWeatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);

    }


}

}
