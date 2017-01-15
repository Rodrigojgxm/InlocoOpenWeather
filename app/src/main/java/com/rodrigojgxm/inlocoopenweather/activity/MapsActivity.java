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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Double mLat;
    private Double mLng;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

    }

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

    public void searchLocation(View v){
        if(mLat != null & mLng != null){
            String url = new StringBuilder().append(getResources().getString(R.string.open_weather_url))
                    .append("&").append("lat=").append(mLat).append("&lon=").append(mLng).append("&cnt=15&APPID=").
                            append(getResources().getString(R.string.open_weather_key)).toString();

            // Create a client to perform networking
            AsyncHttpClient client = new AsyncHttpClient();

            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = ProgressDialog.show(this, getResources().getString(R.string.loading)
                        , getResources().getString(R.string.waiting), true, false);
            }


            // Have the client get a JSONArray of data
            // and define how to respond
            client.get(url,
                    new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Intent it = new Intent(MapsActivity.this,ListResultsActivity.class);
                            it.putExtra(getResources().getString(R.string.open_weather_result_json_key)
                                    ,jsonObject.toString());
                            startActivity(it);
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
