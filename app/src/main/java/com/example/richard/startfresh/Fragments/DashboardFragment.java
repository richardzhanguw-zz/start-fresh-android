package com.example.richard.startfresh.Fragments;

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

import com.example.richard.startfresh.AdaptersAndOtherClasses.ToDoItemToday;
import com.example.richard.startfresh.AdaptersAndOtherClasses.ToDoItemTodayRVAdapter;
import com.example.richard.startfresh.R;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList listOfItems;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ToDoItemTodayRVAdapter rvAdapter;
    public GoogleApiClient mGoogleApiClient;

    public ToDoItemTodayRVAdapter getRvAdapter(){
        return this.rvAdapter;
    }

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String todayAsString = dateFormat.format(today);
        listOfItems= new ArrayList<>();
        if (sharedPref.getInt(todayAsString, 0) > 0){
            for(int i = 1; i < sharedPref.getInt(todayAsString, 0)+1; i ++){
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
        final GifImageView gifImageView = (GifImageView) view.findViewById(R.id.weather_gifview);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView rView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        //listOfItems.add(new ToDoItemToday("Task #1", "Buy Groceries","13:00", "Need to get: eggs, milk, apples, potatoes, orange juice, and a new toothbrush"));
        //listOfItems.add(new ToDoItemToday("Task #2", "Play Basketball", "15:00", "Meet up with friends before hand at the plaza to get lunch first. CIF is open for ball starting at 2pm"));
        //listOfItems.add(new ToDoItemToday("Task #3", "Work on New App", "18:30", "Get the geolocation and videos on the app working before working on other aspects."));
         rvAdapter = new ToDoItemTodayRVAdapter(listOfItems);
        rView.setLayoutManager(llm);
        rView.setAdapter(rvAdapter);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("weather", "permission check passed");
            Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<WeatherResult>() {
                        @Override
                        public void onResult(@NonNull WeatherResult weatherResult) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
