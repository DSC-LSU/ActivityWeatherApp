package com.rohankadkol.activityweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rohankadkol.activityweatherapp.pojos.WeatherResponse;
import com.rohankadkol.activityweatherapp.utils.InternetUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    EditText mEtLocation;
    TextView mTvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtLocation = findViewById(R.id.et_location);
        mTvWeather = findViewById(R.id.tv_weather);
    }

    public void onGoClicked(View view) {
        String location = mEtLocation.getText().toString();
        if (TextUtils.isEmpty(location)) {
            Toast.makeText(this, getString(R.string.error_empty_location), Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Send the request to the API. Get the response.
        //  Get the WeatherResponse from that response.
        //  Then call displayOutput(weatherReponse) with the weatherResponse as a parameter

        // TODO: (After the above TODO) Copy the above code in a loader!
    }

    public void displayOutput(WeatherResponse weatherResponse) {
        // TODO: Create and display an output string using the weatherResponse parameter
        if (weatherResponse == null) {
            Log.d(TAG, "displayOutput: weatherResponse was null");
            return;
        }
    }
}
