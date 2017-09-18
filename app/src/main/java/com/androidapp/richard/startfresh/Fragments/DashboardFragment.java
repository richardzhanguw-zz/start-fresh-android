package com.androidapp.richard.startfresh.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.ToDoItemToday;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.ToDoItemTodayRVAdapter;
import com.androidapp.richard.startfresh.R;
import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;


public class DashboardFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    public ArrayList listOfItems;
    private ToDoItemTodayRVAdapter rvAdapter;
    public GoogleApiClient mGoogleApiClient;

    public ToDoItemTodayRVAdapter getRvAdapter(){
        return this.rvAdapter;
    }

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String todayAsString = dateFormat.format(today);
        listOfItems= new ArrayList<>();
        if (sharedPref.getInt(todayAsString, 0) > 0){
            for(int i = 0; i < sharedPref.getInt(todayAsString, 0); i ++){
                ToDoItemToday task = new ToDoItemToday("task", sharedPref.getString("task" + String.valueOf(i)+"-name", "Task Summary"), sharedPref.getString("task" + String.valueOf(i)+"-time", "Task Time"), sharedPref.getString("task" + String.valueOf(i)+"-details", "Task Details"));
                listOfItems.add(task);
            }
        }
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(),
                        this)
                .addApi(Awareness.API).build();
        mGoogleApiClient.connect();
    }
    public ArrayList getListOfItems(){
        return this.listOfItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView rView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final GifImageView gifImageView = (GifImageView) view.findViewById(R.id.weather_gifview);
        rView.setHasFixedSize(true);
         rvAdapter = new ToDoItemTodayRVAdapter(listOfItems);
        rView.setLayoutManager(llm);
        rView.setAdapter(rvAdapter);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<WeatherResult>() {
                        @Override
                        public void onResult(@NonNull WeatherResult weatherResult) {
                            if (weatherResult.getStatus().isSuccess()) {
                                Weather weather = weatherResult.getWeather();
                                TextView weatherTV = (TextView) view.findViewById(R.id.weather_test_textview);
                                weatherTV.setText("Current Temperature: " + Math.round(weather.getTemperature(Weather.CELSIUS)) + " Celsius" + " Feels like: " + Math.round(weather.getTemperature(Weather.CELSIUS)) + " Celsius");
                                switch(weather.getConditions()[0]) {
                                    case Weather.CONDITION_CLEAR:
                                        gifImageView.setBackgroundResource(R.drawable.weather_sunny);
                                        break;
                                    case Weather.CONDITION_CLOUDY:
                                        gifImageView.setBackgroundResource(R.drawable.weather_cloudy);
                                        break;
                                    case Weather.CONDITION_SNOWY:
                                        gifImageView.setBackgroundResource(R.drawable.weather_snowy);
                                        break;
                                    case Weather.CONDITION_RAINY:
                                        gifImageView.setBackgroundResource(R.drawable.weather_rainy);
                                        break;
                                    case Weather.CONDITION_STORMY:
                                        break;
                                    default:
                                        gifImageView.setBackgroundResource(R.drawable.weather_sunny);
                                        break;
                                };
                            } else {
                                Log.d("weather1", weatherResult.getStatus().toString());
                            }
                        }
                    });
        }
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
