package comayushs08.github.argus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;

    private FusedLocationProviderClient mfusedLocationClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 1 * 100;
    private long FASTEST_INTERVAL = 200;
    private static final int locationRequestResult = 2018;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 4;
    private static final String[] PERMISSIONS_LOCATION = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private Geocoder geocoder;
    private static String geoLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabHost);
        ViewPager viewPager = findViewById(R.id.pageHost);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new TabHome(), "Home");
        viewPagerAdapter.addFragments(new TabContacts(), "Contacts");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        mfusedLocationClient = getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        getLocationPermission();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    protected void getLocationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    requestPermissions(PERMISSIONS_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                else {
                    requestPermissions(PERMISSIONS_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return;
            }
            else {
                startLocationUpdates();
            }
        }
        else {
            startLocationUpdates();
        }

    }

    @SuppressLint({"RestrictedApi", "MissingPermission"})
    protected  void startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        // Check whether location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());



        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                locationRequestResult);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    private void onLocationChanged(Location location) {

        geoLocation = "";

        geoLocation += location.getLatitude() + "," + location.getLongitude();

        String msg = "Lat: " +
                Double.toString(location.getLatitude()) + "\n\nLong: " +
                Double.toString(location.getLongitude());

        Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();

        List<Address> address;

        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            msg = msg + "\n\nAddress: " + address.get(0).getAddressLine(0).toString();
        }  catch (IOException ioException) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT).show();
        }

        TextView textView = findViewById(R.id.homeHelperText);
        textView.setText(msg);
    }

    protected String getCoordinates() {
        return geoLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Please Turn On Location Service", Toast.LENGTH_SHORT).show();
                    }
            }
        }
        return;
    }
}
