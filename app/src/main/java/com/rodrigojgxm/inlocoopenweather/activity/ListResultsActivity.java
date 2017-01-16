package com.rodrigojgxm.inlocoopenweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rodrigojgxm.inlocoopenweather.R;
import com.rodrigojgxm.inlocoopenweather.adapter.CityWeatherAdapter;
import com.rodrigojgxm.inlocoopenweather.model.CityWeatherWrapper;
import com.rodrigojgxm.inlocoopenweather.util.OpenWeatherJsonParser;

import java.util.List;

/**
 * Classe responsavel por exibir a lista de resultados
 */
public class ListResultsActivity extends Activity implements AdapterView.OnItemClickListener {
    private List<CityWeatherWrapper> mCityResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_results);

        String resultJson = getIntent().getStringExtra(getResources()
                .getString(R.string.open_weather_result_json_key));
        mCityResultList = OpenWeatherJsonParser.parse(resultJson);

        ListView listView = (ListView) findViewById(R.id.search_result);
        listView.setAdapter(new CityWeatherAdapter(this,mCityResultList));
        listView.setOnItemClickListener(this);

    }

    /**
     * Método responsavel por responder ao evento de click no item da lista
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CityWeatherWrapper cityWeatherWrapper = mCityResultList.get(position);
        Intent it = new Intent(ListResultsActivity.this,CityInfoActivity.class);
        it.putExtra(getResources().getString(R.string.city_wrapper_key),cityWeatherWrapper);
        startActivity(it);
    }
}
