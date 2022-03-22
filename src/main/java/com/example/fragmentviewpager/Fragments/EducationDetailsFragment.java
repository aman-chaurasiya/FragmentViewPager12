package com.example.fragmentviewpager.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragmentviewpager.DetailsActivity;
import com.example.fragmentviewpager.MainActivity;
import com.example.fragmentviewpager.R;
import com.example.fragmentviewpager.Room.Database;
import com.example.fragmentviewpager.Room.DetailsModel;
import com.example.fragmentviewpager.databinding.FragmentCallBinding;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class EducationDetailsFragment extends Fragment {
    FragmentCallBinding binding;

    Database myDataBase;
    DetailsModel dm, dm2;

    String mode = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call_, container, false);
        View view = binding.getRoot();
        myDataBase = Room.databaseBuilder(getContext(), Database.class, "DetailsDB")
                .allowMainThreadQueries().build();
        if (getActivity().getIntent() != null) {
            mode = getActivity().getIntent().getStringExtra("mode");
            //  Log.d("TAG", "onCreateView: " + mode);
        }

        if (mode != null && mode.equals("edit")) {
            dm2 = myDataBase.dao().getData();
            if (dm2 != null) {

                binding.highQulifn.setText(dm2.getHighestQualification());
                binding.edtUniversity.setText(dm2.getUniversity());
                binding.edtCollege.setText(dm2.getCollege());
                binding.edtPercent.setText(dm2.getPercentage());
            }
        }

        getdata();


        return view;
    }

    private void getdata() {

        getParentFragmentManager().setFragmentResultListener("Contact Details", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {


                dm = result.getParcelable("Contact Details");
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtPercent.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter all field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.highQulifn.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter all field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.edtUniversity.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter all field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.edtCollege.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please enter all field", Toast.LENGTH_SHORT).show();
                    return;
                }

                dm.setId(1);
                dm.setHighestQualification(binding.highQulifn.getText().toString());
                dm.setUniversity(binding.edtUniversity.getText().toString());
                dm.setCollege(binding.edtCollege.getText().toString());
                dm.setPercentage(binding.edtPercent.getText().toString());
                Log.d("TAG", "onClick: " + dm.toString());
                myDataBase = Room.databaseBuilder(getContext(), Database.class, "DetailsDB")
                        .allowMainThreadQueries().build();


                if (myDataBase.dao().getData() != null) {
                    //  myDataBase.dao().deleteAll();
                    myDataBase.dao().updateData(dm);
                } else {
                    myDataBase.dao().detailsInsert(dm);
                }
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }


}
