package com.example.shoppinglist.service.localDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoppinglist.service.localDb.entity.Category;
import com.example.shoppinglist.service.localDb.entity.Items;

@Database(entities = {Category.class, Items.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract ShoppingListDao shoppingListDao();
    public static AppRoomDatabase Instance;

    //define a singleton , it will return.
    public static AppRoomDatabase getDbInstance(Context context){
        if (Instance == null){
            Instance = Room.databaseBuilder(context.getApplicationContext(),AppRoomDatabase.class,"ApDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return Instance;
    }
}
