package com.rodrigojgxm.inlocoopenweather.util;

import com.rodrigojgxm.inlocoopenweather.model.CityWeatherWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsavel por converter os dados recebidos na requisição em objetos do tipo CityWeatherWrapper
 * Created by Rodrigojgxm.
 */

public class OpenWeatherJsonParser {
    public static List<CityWeatherWrapper> parse(String data){
        List<CityWeatherWrapper> cityWeatherList = new ArrayList<CityWeatherWrapper>();
        try {
            JSONObject jObj = new JSONObject(data);
            JSONArray cityList = jObj.getJSONArray("list");
            JSONObject cityInfo;
            JSONObject main;
            JSONArray weather;
            JSONObject weatherItem;
            CityWeatherWrapper cityWeatherWrapper;

            for (int i = 0; i<cityList.length();i++){
                cityWeatherWrapper = new CityWeatherWrapper();
                cityInfo = cityList.getJSONObject(i);
                cityWeatherWrapper.setName(cityInfo.getString("name"));
                main = cityInfo.getJSONObject("main");
                cityWeatherWrapper.setMinTemp(main.getDouble("temp_min"));
                cityWeatherWrapper.setMaxTemp(main.getDouble("temp_max"));

                weather  = cityInfo.getJSONArray("weather");
                weatherItem = weather.getJSONObject(0);
                cityWeatherWrapper.setDescription(weatherItem.getString("description"));

                cityWeatherList.add(cityWeatherWrapper);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cityWeatherList;
    }
}
