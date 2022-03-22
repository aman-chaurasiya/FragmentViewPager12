package com.example.fragmentviewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fragmentviewpager.Fragments.BasicDetailsFragment;
import com.example.fragmentviewpager.Room.Database;
import com.example.fragmentviewpager.Room.DetailsModel;
import com.example.fragmentviewpager.databinding.ActivityDetailsBinding;

import java.io.File;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    Database myDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        myDataBase = Room.databaseBuilder(this, Database.class, "DetailsDB")
                .allowMainThreadQueries().build();

        DetailsModel dm = myDataBase.dao().getData();
      String  imagepath = dm.getImage();
        //Log.e("IMAGEPATH", "edit "+imagepath );


        binding.ivImageView.setImageURI(Uri.fromFile(new File(imagepath)));
       // binding.ivImageView.setImageURI(Uri.parse(dm.getImage()));
        binding.tvName.setText(dm.getName());
        binding.tvMobile.setText(dm.getMobile());
        binding.tvEmail.setText(dm.getEmail());
        binding.tvAddress.setText(dm.getAddress());
        binding.tvPinCode.setText(dm.getPinCode());
        binding.tvAltMobile.setText(dm.getAlternateMobile());
        binding.tvHighQlfy.setText(dm.getHighestQualification());
        binding.tvUniversity.setText(dm.getUniversity());
        binding.tvCollege.setText(dm.getCollege());
        binding.tvPercentage.setText(dm.getPercentage());

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra("mode", "edit");
                startActivity(intent);
                finish();


            }
        });

    }
}