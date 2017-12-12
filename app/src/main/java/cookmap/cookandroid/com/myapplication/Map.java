package cookmap.cookandroid.com.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by 507 on 2017-11-27.
 */

public class Map extends MainActivity {
    public TextView txLat, txLong, txState;
    public Button btLoc, btMap, btReturn;
    public LocationManager mLocationMan;
    public MyLocationListener myLocationLx;
    public double latitude, longitude, altitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        txLat = (TextView) findViewById(R.id.txLat);
        txLong = (TextView) findViewById(R.id.txLong);
        txState = (TextView) findViewById(R.id.txState);
        btLoc = (Button) findViewById(R.id.btLoc);
        btReturn = (Button) findViewById(R.id.btReturn);
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txState.setText("Getting location...");
                latitude = myLocationLx.latitude;
                longitude = myLocationLx.longitude;
                altitude = myLocationLx.altitude;
                txLat.setText(String.format("%g", latitude));
                txLong.setText(String.format("%g", longitude));
                txState.setText("Done retreiving.");
            }
        });
        btMap = (Button) findViewById(R.id.btMap);
        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sLoc = String.format("geo:%g,%g?z=17", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sLoc));
                startActivity(intent);
            }
        });





        mLocationMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocationLx = new MyLocationListener();
        long minTime = 1000;   // minimum time interval between location updates, in milliseconds
        float minDistance = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationLx);
    }
}