package com.example.fragmentviewpager.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.room.Room;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentviewpager.MainActivity;
import com.example.fragmentviewpager.R;
import com.example.fragmentviewpager.Room.Database;
import com.example.fragmentviewpager.Room.DetailsModel;
import com.example.fragmentviewpager.databinding.FragmentChatBinding;
import com.example.fragmentviewpager.databinding.FragmentStatusBinding;


public class ContactDetailsFragment extends Fragment {
    FragmentStatusBinding binding;

    DetailsModel dm;
    DetailsModel dm2;
    Database myDataBase;
    String mode = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_status_, container, false);
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

                binding.edtAddressLine1.setText(dm2.getAddress());
                binding.edtAddressLine2.setText(dm2.getAddress());
                binding.edtPinCode.setText(dm2.getPinCode());
                binding.edtAlterMobile.setText(dm2.getAlternateMobile());
            }
        }
        getParentFragmentManager().setFragmentResultListener("Basic Details", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                dm = result.getParcelable("Basic Details");
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtAlterMobile.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter All details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.edtAddressLine1.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter All details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.edtAddressLine2.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter All details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.edtPinCode.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter All details", Toast.LENGTH_SHORT).show();
                    return;
                }
                dm.setAddress(binding.edtAddressLine1.getText().toString());
                dm.setPinCode(binding.edtPinCode.getText().toString());
                dm.setAlternateMobile(binding.edtAlterMobile.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putParcelable("Contact Details", dm);
                getParentFragmentManager().setFragmentResult("Contact Details", bundle);
                ((MainActivity) getActivity()).viewPager.setCurrentItem(2);
            }
        });
        return view;
    }
}