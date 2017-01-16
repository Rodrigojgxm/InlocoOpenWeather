package com.rodrigojgxm.inlocoopenweather.activity;

import android.app.Activity;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rodrigojgxm.inlocoopenweather.R;
import com.rodrigojgxm.inlocoopenweather.model.CityWeatherWrapper;

/**
 * Classe responsavel por exibir os dados de informações da cidade escolhida.
 */
public class CityInfoActivity extends Activity {
    private CityWeatherWrapper mWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);
        mWrapper = (CityWeatherWrapper) getIntent().getSerializableExtra(getResources()
                .getString(R.string.city_wrapper_key));

        ((TextView) findViewById(R.id.name_info)).setText(mWrapper.getName());
        ((TextView) findViewById(R.id.max_temp_value)).setText(mWrapper.getMaxTemp()+"°C");
        ((TextView) findViewById(R.id.min_temp_value)).setText(mWrapper.getMinTemp()+"°C");
        ((TextView) findViewById(R.id.description_value)).setText(mWrapper.getDescription());
    }
}
