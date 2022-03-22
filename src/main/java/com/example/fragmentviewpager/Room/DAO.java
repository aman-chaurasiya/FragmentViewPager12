package com.example.fragmentviewpager.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    public void detailsInsert(DetailsModel detailsModel);


    @Update
    public void updateData(DetailsModel detailsModel);


    @Query("select * from DetailsModel")
    public DetailsModel getData();


    @Query("DELETE FROM DetailsModel")
    void deleteAll();


}
