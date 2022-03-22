package com.example.fragmentviewpager.Room;

import androidx.room.Entity;
import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {DetailsModel.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DAO dao();


}
