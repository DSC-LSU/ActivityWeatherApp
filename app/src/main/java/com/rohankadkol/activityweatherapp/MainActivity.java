package com.rohankadkol.activityweatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherResponse> {
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

        Bundle bundle = new Bundle();
        bundle.putString("location", location);
        getSupportLoaderManager().initLoader(0, bundle, this);

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
        String output = "";
        output += "Temp: " + weatherResponse.getMain().getTemp() + "\n";
        output += "Min Temp: " + weatherResponse.getMain().getTempMin() + "\n";
        output += "Max Temp: " + weatherResponse.getMain().getTempMax() + "\n";

        mTvWeather.setText(output);
    }

    @NonNull
    @Override
    public Loader<WeatherResponse> onCreateLoader(int id, @Nullable Bundle args) {
        String location = args.getString("location");
        return new CustomLoader(this, location);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<WeatherResponse> loader, WeatherResponse weatherResponse) {
        displayOutput(weatherResponse);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<WeatherResponse> loader) {

    }

    public static class CustomLoader extends AsyncTaskLoader<WeatherResponse> {
        String location;

        public CustomLoader(@NonNull Context context, String location) {
            super(context);
            this.location = location;
            forceLoad();
        }

        @Nullable
        @Override
        public WeatherResponse loadInBackground() {
            WeatherResponse weatherResponse = null;
            try {
                Uri uri = InternetUtils.makeRequestUri(location);
                URL url = new URL(uri.toString());
                String jsonResponse = InternetUtils.makeHttpRequest(url);
                weatherResponse = InternetUtils.extractWeatherFromJson(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherResponse;
        }
    }
}
