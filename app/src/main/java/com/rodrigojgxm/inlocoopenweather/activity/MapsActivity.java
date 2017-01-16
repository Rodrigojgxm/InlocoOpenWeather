package com.rodrigojgxm.inlocoopenweather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rodrigojgxm.inlocoopenweather.R;

import org.json.JSONObject;

/**
 * Classe responsavel pelas atividades do mapa e tela inicial da aplicação
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Double mLat;
    private Double mLng;
    private ProgressDialog progressDialog;

    /**
     * Método que carrega o elemento do mapa na activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Método que exibe o mapa na activity
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }

    /**
     * Método que adiciona um marcador e recupera a latitude e longitude do ponto clicado
     * @param point
     */
    @Override
    public void onMapClick(LatLng point) {
        if (point != null){
            mMap.clear();
        }
        mLat = point.latitude;
        mLng = point.longitude;

        String latLngComplete = getResources().getString(R.string.latitude) + mLat + " "
                + getResources().getString(R.string.longitude) + mLng;

        mMap.addMarker(new MarkerOptions().position(point).title(latLngComplete).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

    }

    /**
     * Método responsavel por requisitar dados da api OpenWeather ao clicar no botão
     * @param v
     */
    public void searchLocation(View v){
        if(mLat != null & mLng != null){
            String url = new StringBuilder().append(getResources().getString(R.string.open_weather_url))
                    .append("&").append("lat=").append(mLat).append("&lon=").append(mLng).append("&cnt=15&APPID=").
                            append(getResources().getString(R.string.open_weather_key)).toString();

            AsyncHttpClient client = new AsyncHttpClient();
            // Mostra a progress bar enquanto a requisição está sendo processada
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = ProgressDialog.show(this, getResources().getString(R.string.loading)
                        , getResources().getString(R.string.waiting), true, false);
            }

            client.get(url,
                    new JsonHttpResponseHandler() {
                        /**
                         * verifica se a requisição obteve sucesso ou falha, em caso de sucesso
                         * a listRestultsActiviy é chamada com os objetos obtidos no JSON em formato
                         * de String
                         *
                         * @param jsonObject
                         */
                        @Override
                        public void onSuccess(JSONObject jsonObject) {

                            Intent it = new Intent(MapsActivity.this,ListResultsActivity.class);
                            it.putExtra(getResources().getString(R.string.open_weather_result_json_key)
                                    ,jsonObject.toString());
                            startActivity(it);
                            //retira a progress bar
                            progressDialog.dismiss();


                        }

                        @Override
                        public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                            Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("RJGXM", statusCode + " " + throwable.getMessage());
                            progressDialog.dismiss();

                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.search_error), Toast.LENGTH_LONG).show();
        }


    }
}
