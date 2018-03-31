package comayushs08.github.argus;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;
//    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 4;
//    private static final String[] PERMISSIONS_LOCATION = {Manifest.permission.INTERNET ,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @RequiresApi(api = Build.VERSION_CODES.M)
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

//        getLocationPermission();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    protected void getLocationPermission() {
//        for (String permission : PERMISSIONS_LOCATION) {
//            if (checkSelfPermission(permission)
//                    != PackageManager.PERMISSION_GRANTED) {
//                if (shouldShowRequestPermissionRationale(permission)) {
//                    requestPermissions(PERMISSIONS_LOCATION,
//                            MY_PERMISSIONS_REQUEST_LOCATION);
//                } else {
//                    requestPermissions(PERMISSIONS_LOCATION,
//                            MY_PERMISSIONS_REQUEST_LOCATION);
//                }
//            }
//        }
//    }

}
