package com.rodrigojgxm.inlocoopenweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.inlocomedia.android.InLocoMedia;
import com.inlocomedia.android.InLocoMediaOptions;
import com.inlocomedia.android.ads.AdError;
import com.inlocomedia.android.ads.AdRequest;
import com.inlocomedia.android.ads.interstitial.InterstitialAd;
import com.inlocomedia.android.ads.interstitial.InterstitialAdListener;
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
     * Método utilizado para exibir o anúncio interstial Inloco media
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        InLocoMediaOptions options = InLocoMediaOptions.getInstance(this);
        options.setAdsKey(getResources().getString(R.string.inLoco_key));
        options.setLogEnabled(true);
        options.setDevelopmentDevices(getResources().getString(R.string.inLoco_device_id));
        InLocoMedia.init(this, options);

        InterstitialAd interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setInterstitialAdListener(new InterstitialAdListener() {

            @Override
            public void onAdReady(final InterstitialAd ad) {
                ad.show();
            }

            @Override
            public void onAdError(InterstitialAd ad, AdError error) {
                Log.w("InLocoMedia", "Your interstitial has failed with error: " + error);
            }
        });

        AdRequest adRequest = new AdRequest();
        interstitialAd.loadAd(adRequest);

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
