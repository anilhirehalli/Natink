package engin.natink;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

/**
 * Created by anilkumar on 6/3/18.
 */

public class notifi extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    // Toast.makeText(getApplicationContext(),"on provide enabled is called",Toast.LENGTH_SHORT).show();
    //NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    Notification noti = new Notification.Builder(this)
            .setContentTitle("S.O.S - Natink")
            .setContentText(" to your Friends via Natink")
            .setSmallIcon(R.drawable.engin)
            .setPriority(Notification.PRIORITY_MAX)
            .setOngoing(true) // Again, THIS is the important line
            .build();


     // notificationManager.notify(0,noti);



}
