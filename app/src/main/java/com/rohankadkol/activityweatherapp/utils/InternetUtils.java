package com.rohankadkol.activityweatherapp.utils;

import android.net.Uri;
import android.util.Log;

import com.rohankadkol.activityweatherapp.pojos.WeatherResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class InternetUtils {
    private static final String TAG = InternetUtils.class.getSimpleName();
    // TODO: Enter API KEY here
    private static final String API_KEY = "";
    private static final String SCHEME = "https";
    private static final String AUTHORITY = "api.openweathermap.org";

    private static final String KEY_LOCATION = "q";
    private static final String KEY_API_KEY = "appid";

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "makeHttpRequest: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a {@link WeatherResponse} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    public static WeatherResponse extractWeatherFromJson(String jsonResponse) {
        // TODO: Parse the JSON response to retrieve a WeatherResponse object
        WeatherResponse weatherResponse = null;
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject main = root.getJSONObject("main");
            double temp = main.getDouble("temp");
            double tempMin = main.getDouble("temp_min");
            double tempMax = main.getDouble("temp_max");
            JSONArray weatherList = root.getJSONArray("weather");
            JSONObject weather = weatherList.getJSONObject(0);
            String mainString = weather.getString("main");
            String descriptionString = weather.getString("description");
            weatherResponse = new WeatherResponse(temp, tempMin, tempMax, mainString, descriptionString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherResponse;
    }

    public static Uri makeRequestUri (String location) {
        // Sample Uri: http://api.openweathermap.org/data/2.5/weather?q=london&appid=<your_api_key>
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.authority(AUTHORITY);
        builder.appendPath("data");
        builder.appendPath("2.5");
        builder.appendPath("weather");
        builder.appendQueryParameter(KEY_LOCATION, location);
        builder.appendQueryParameter(KEY_API_KEY, API_KEY);
        return builder.build();
    }
}
