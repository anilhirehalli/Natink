package engin.natink;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class hist extends AppCompatActivity {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    ProgressDialog progressDialog;
    TextView firstline,secondline,thirdline;
    private static final int MY_PERMISSIONS_REQUESTED_SEND_SMS = 0;
    ArrayList<String> sendmessage = new ArrayList<String>();
    double latitude,longitude;
    int i,j,sendMessage=0;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);

        firstline = (TextView)findViewById(R.id.firstline);
        secondline = (TextView)findViewById(R.id.secondline);
        thirdline = (TextView)findViewById(R.id.thirdline);
        progressDialog = new ProgressDialog(hist.this);
        progressDialog.setMessage("Please wait..."); // Setting Message
        progressDialog.setTitle("Getting location"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }


            @Override
            public void onFinish() {
                configureButton();
            }
        }.start();

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                progressDialog.setCancelable(false);
                progressDialog.dismiss();
                //Toast.makeText(getApplicationContext(),"on location chanaged ",Toast.LENGTH_SHORT).show();
                i=25;
                j++;
                if(j==1) {
                    int permissioncheck = ContextCompat.checkSelfPermission(hist.this, Manifest.permission.SEND_SMS);
                    if (permissioncheck == PackageManager.PERMISSION_GRANTED) {
                        loaddata();


                        youcansend();
                    } else {
                        ActivityCompat.requestPermissions(hist.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUESTED_SEND_SMS);
                    }


                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 2000.0f, locationListener);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                },10);
                return;
            }
        }
        else
        {
            configureButton();
        }




    }

    private void youcansend() {
        try {
            String text = " is to inform you that your Friend is in need of your help. this link genrated by Natink. http://maps.google.com/maps?q=loc:";//+""+ latitude + "," + ""+longitude;
            String message = text + latitude + "," + longitude;
         //   Toast.makeText(getApplicationContext(), latitude + "   " + longitude + "", Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_SHORT).show();
            for (int k = 0; k < sendmessage.size(); k++) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(sendmessage.get(k), null, "This" + message, null, null);
               // Toast.makeText(getApplicationContext(), "send hasbeen called", Toast.LENGTH_SHORT).show();
                firstline.setVisibility(View.VISIBLE);
                secondline.setVisibility(View.VISIBLE);
                thirdline.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Message has not been send do report us..!", Toast.LENGTH_SHORT).show();
        }

    }

    private void loaddata() {
        if(sendmessage==null)
        {

        }
        else
        {

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list", null);
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            sendmessage = gson.fromJson(json, type);



            // Toast.makeText(getApplicationContext(), contacts+" contacts", Toast.LENGTH_SHORT).show();
            // Toast.makeText(getApplicationContext(), sendmessage + " sendmessage", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  10:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    @SuppressLint("MissingPermission")
    private void configureButton() {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);


    }
}
