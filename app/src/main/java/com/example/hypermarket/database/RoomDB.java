package com.example.hypermarket.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = CustomerAddressModel.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    public static final String DATABASE_NAME = "digikala.db";
    public abstract AddressDao customerAddressDao();
    private static RoomDB instance;

    public static synchronized RoomDB getInstance(Context context) {
        if (instance == null) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class,
                            DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        return instance;
    }
}

