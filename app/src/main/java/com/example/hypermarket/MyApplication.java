package com.example.finalproject;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.finalproject.database.RoomDB;

public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public RoomDB getRoomDb() {
        return roomDb;
    }

    private RoomDB roomDb;

    @Override
    public void onCreate() {
        super.onCreate();
        roomDb = RoomDB.getInstance(this);
        createNotificationChannel();
        mInstance = this;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String  name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String channelId = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId , name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

