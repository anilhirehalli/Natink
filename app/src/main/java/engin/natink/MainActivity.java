package engin.natink;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private static final int MY_PERMISSIONS_REQUESTED_SEND_SMS = 0;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    double latitude,longitude;
    ProgressDialog progressDialog;
    ArrayList<String> sendmessage = new ArrayList<String>();
    String uriString;
    int i=0,j=0,locationSent=0,sendMessage=0, initial1,number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissioncheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);
        if(permissioncheck== PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUESTED_SEND_SMS);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED ) {
            //Name of Method for Calling Message


        } else {
            //TODO
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        checkvalue();
        setvalue();
       // Toast.makeText(getApplicationContext(),"initial = "+initial1,Toast.LENGTH_SHORT).show();
        if(initial1==2)
        {
         Intent a = new Intent(MainActivity.this,Reportus.class);
         startActivity(a);
          finish();

        }
        if(initial1==3)
        {
            Intent a1 = new Intent(MainActivity.this,message.class);
            startActivity(a1);


        }

        //setvalue();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
       getnotification();
       //geterrornotifcation();
       Intent intent = new Intent(getApplicationContext(),notifi.class);
       startService(intent);
       /* NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Notification title")
                .setContentText("Notification content")
                .setSmallIcon(R.drawable.engin)
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true) // Again, THIS is the important line
                .build();


        notificationManager.notify(0,noti);
         Intent intent = new Intent(getApplicationContext(),sendmessage.class);*/



        //textView5 = (TextView)findViewById(R.id.textView5);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener =  new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
               // Toast.makeText(getApplicationContext(),"on location chanaged ",Toast.LENGTH_SHORT).show();
                i=25;
                j++;

               progressDialog.setCancelable(false);
                progressDialog.dismiss();

               if(j==1&&locationSent ==1) {
                    Intent ShareingIntent = new Intent(Intent.ACTION_SEND);
                    ShareingIntent.setType("text/plain");
                    ShareingIntent.putExtra(Intent.EXTRA_TEXT, "This is to inform you that your Friend is in need of your help. this link genrated by Natink\n" +
                            "to locate your friend. " + "\n" + "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude);
                    startActivity(ShareingIntent);
                    savelongitude();
                    savelatitude();
                }
                if(j==1&&sendMessage ==1) {
                    int permissioncheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);
                    if(permissioncheck== PackageManager.PERMISSION_GRANTED)
                    {
                        loaddata();


                        youcansend();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUESTED_SEND_SMS);
                    }


                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Location location = new Location(LocationManager.GPS_PROVIDER);
                latitude=location.getLatitude();
                longitude=location.getLongitude();
              //  Toast.makeText(getApplicationContext(),"on provide enabled is called",Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(),latitude+" latitdue "+longitude+"longitude",Toast.LENGTH_SHORT).show();
               // i=25;

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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //app_bar_main.xml
        //removed messages
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, message.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }






    private void getnotification() {
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0,
                new Intent(this, hist.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("S.O.S - Natink")
                .setContentText("Press the Send Emergency message to send the location for your friends")
                .setSmallIcon(R.drawable.engin)
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true)// Again, THIS is the important line
                .addAction(R.mipmap.ic_launcher_round,"Send Emergency message",pendingintent)
                .build();


       notificationManager.notify(0,noti);
     //  Intent intent = new Intent(MainActivity.this, notification.class);
      // startService(intent);
        // Intent notif = new Intent(MainActivity.this,test.class);
        //startActivity(notif);
       /* NotificationManagerCompat myManager= NotificationManagerCompat.from(this);
        NotificationCompat.Builder mynotify=new NotificationCompat.Builder(this);
        mynotify.setContentTitle("1 New Message");
        mynotify.setContentText("Hi How are you");
        mynotify.setSmallIcon(android.R.drawable.ic_btn_speak_now);
       // Intent i=new Intent(this,sendmessage.class);
       // PendingIntent pd=PendingIntent.getActivity(this,1,i,0);
      //  mynotify.setContentIntent(pd);
        mynotify.setAutoCancel(true);
        myManager.notify(1,mynotify.build());*/



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  10:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton() {
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 000, 0, locationListener);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ourlocat) {

                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Please wait..."); // Setting Message
                    progressDialog.setTitle("Getting location"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                  //  progressDialog.setCancelable(false);
                  /*  new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(10000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                           // progressDialog.dismiss();
                       // }
                    //}).start();
                    locationSent=1;

                    j=0;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 300, locationListener);
            if (i==25) {

            //  final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
            //  simpleProgressBar.setVisibility(View.VISIBLE);
            /*Intent ShareingIntent = new Intent(Intent.ACTION_SEND);
            ShareingIntent.setType("text/plain");
            ShareingIntent.putExtra(Intent.EXTRA_TEXT, "This is to inform you that your Friend is in need of your help. this link genrated by Natink\n" +
                    "to locate your friend. " + "\n"  + "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude);
            startActivity(ShareingIntent);
            savelongitude();
            savelatitude();*/
            //simpleProgressBar.setVisibility(View.INVISIBLE);
            }
            else
            {
               // Toast.makeText(getApplicationContext(),"else part is working",Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 300, locationListener);

            }

        }
        //settings at the dots

        return super.onOptionsItemSelected(item);
    }

    private void savelatitude() {
        SharedPreferences settings = getSharedPreferences("YOUR_PREF_NAME2", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("latitude", (float) latitude);
        editor.commit();
    }

    private void savelongitude() {
        SharedPreferences settings = getSharedPreferences("YOUR_PREF_NAME1", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("longitude", (float) longitude);
        editor.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


         /*String geoUri = "http://maps.google.com/maps?q=loc:" + "12.2958 "+ "," +" 76.6394" ;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(intent);*/
       /* if ( id == R.id.action_history) {

          //  turnGPSOn();
            // Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
           // intent.putExtra("enabled", true);
            //sendBroadcast(intent);
          Intent i = new Intent(MainActivity.this,hist.class);
          startActivity(i);
        }
         else */
             if ( id == R.id.action_share) {

            Intent ShareingIntent = new Intent(Intent.ACTION_SEND);
            ShareingIntent.setType("text/plain");
            ShareingIntent.putExtra(Intent.EXTRA_TEXT, "What to do while you are in Danger? "+"\n"+"Use our S.O.S app to get yourself out of danger with the Help of our app Natink" + "\n" + " To download" + "\n" + "https://drive.google.com/open?id=0B7jzP24_Ndx1YWY4MXliQW4wcU0");
            startActivity(ShareingIntent);
        }
        //menu - activity_main_drawer.xml
       /* else if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            }*/
        /*else if (id == R.id.rec) {

        }*/
        else if (id == R.id.feed) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.setPackage("com.google.android.gm");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"engin.mysuru@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Feed back");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }
        else if (id == R.id.repus) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.setPackage("com.google.android.gm");
            // i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"engin.mysuru@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Reporting a bug .(do include a screen shot)");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.lik_e) {
            try {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                uriString = "http://m.facebook.com/EngINIndia/";
                sharingIntent.putExtra(Intent.EXTRA_TEXT,uriString);
                sharingIntent.setPackage("com.facebook.katana");
                startActivity(sharingIntent);

//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/FXpB/U9j/xZ"));
            //    Toast.makeText(getApplicationContext(),"try is running",Toast.LENGTH_SHORT).show();

//                startActivity(intent);
            } catch(Exception e) {
                String url="m.facebook.com/EngINIndia/";
                Uri u= Uri.parse("http://"+url);
                Intent i=new Intent(Intent.ACTION_VIEW,u);
                startActivity(i);
            }

        /*  try {
              Intent sharingIntent = new Intent(Intent.ACTION_SEND);
              sharingIntent.setType("text/plain");
              uriString = "";
              sharingIntent.putExtra(Intent.EXTRA_TEXT,uriString);
              sharingIntent.setPackage("com.facebook.katana");
              startActivity(sharingIntent);

          }catch (PackageManager.NameNotFoundException e)
          {}*/



            /*           Intent intent = new Intent(MainActivity.this, aboutus.class);
            startActivity(intent);
*/


        } else if (id == R.id.eng_in) {
            Intent intent = new Intent(MainActivity.this, aboutus.class);
            startActivity(intent);



        }
        /*else if (id == R.id.log_out) {
            Intent intent = new Intent(MainActivity.this, second.class);
            startActivity(intent);
            finish();



        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }



        // PackageManager pacman = getPackageManager();
        //PackageInfo pacInfo = null;

       /* try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
            Toast.makeText(getApplicationContext(),"try is called ",Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
           Toast.makeText(getApplicationContext(),"catch is called ",Toast.LENGTH_SHORT).show();
            return false; //package not found
        }

        if (pacInfo != null) {
            for (ActivityInfo actInfo : pacInfo.receivers) {
                //test if recevier is exported. if so, we can toggle GPS.
                Toast.makeText(getApplicationContext(),"for is called ",Toast.LENGTH_SHORT).show();
                if (actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported) {
                    Toast.makeText(getApplicationContext(),"if is called ",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }

        return false; *///default

    public void youcansend() {
        //getlatitude();
        //getlongitude();
        try {
            String text = " is to inform you that your Friend is in need of your help. this link genrated by Natink. http://maps.google.com/maps?q=loc:";//+""+ latitude + "," + ""+longitude;
            String message = text + latitude + "," + longitude;
           // Toast.makeText(getApplicationContext(), latitude + "   " + longitude + "", Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_SHORT).show();
            for (int k = 0; k < sendmessage.size(); k++) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(sendmessage.get(k), null, "This" + message, null, null);
              //  Toast.makeText(getApplicationContext(), "send hasbeen called", Toast.LENGTH_SHORT).show();
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
    private void checkvalue() {
        SharedPreferences settings1 = getSharedPreferences("Begining", 0);
        initial1 = settings1.getInt("SNOW_DENSITY1", 0);
    }
    private void setvalue() {

        initial1++;
        SharedPreferences settings1 = getSharedPreferences("Begining", 0);
        SharedPreferences.Editor editor1 = settings1.edit();
        editor1.putInt("SNOW_DENSITY1",initial1);
        editor1.commit();
    }
   @SuppressLint("MissingPermission")
   public void sendmessagefun(View view){
        sendMessage=1;
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 300, locationListener);
   }
}




