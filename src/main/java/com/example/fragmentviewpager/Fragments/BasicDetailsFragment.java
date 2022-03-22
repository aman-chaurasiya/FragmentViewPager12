package com.example.fragmentviewpager.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentviewpager.MainActivity;
import com.example.fragmentviewpager.R;
import com.example.fragmentviewpager.Room.Database;
import com.example.fragmentviewpager.Room.DetailsModel;
import com.example.fragmentviewpager.databinding.FragmentChatBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class BasicDetailsFragment extends Fragment {

    FragmentChatBinding binding;
    ActivityResultLauncher<Intent> cameraResultLauncher;
    ActivityResultLauncher<Intent> gallaryResultLauncher;
    Bitmap bitmap;
    String imagepath;
    Uri ImageUri;
    String mode = "";
    Database myDataBase;
    DetailsModel dm2;
    File file;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_, container, false);
        View view = binding.getRoot();
        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            bitmap = (Bitmap) bundle.get("data");
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                           String path = MediaStore.Images.Media.insertImage(getContext().
                                    getContentResolver(), bitmap, "Camera Image", null);

                            ImageUri = Uri.parse(path);
                            imagepath=getRealPathFromURI(ImageUri);
                            binding.ivImage.setImageURI(ImageUri);
                        } else {
                            Toast.makeText(getContext(), "No Image Captured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        gallaryResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null) {

                            ImageUri = result.getData().getData();

                            Log.e("PATH", "onActivityResult: "+getRealPathFromURI(ImageUri));
                            imagepath=getRealPathFromURI(ImageUri);
                          //  file = new File(getRealPathFromURI(ImageUri));


                            binding.ivImage.setImageURI(ImageUri);
                        } else {
                            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        myDataBase = Room.databaseBuilder(getContext(), Database.class, "DetailsDB")
                .allowMainThreadQueries().build();

        if (getActivity().getIntent() != null) {
            mode = getActivity().getIntent().getStringExtra("mode");
          //  Log.d("TAG", "onCreateView: " + mode);
        }

        if (mode != null && mode.equals("edit")) {
            dm2 = myDataBase.dao().getData();
            if (dm2 != null) {
                imagepath = dm2.getImage();
                Log.e("IMAGEPATH", "edit "+imagepath );
               binding.ivImage.setImageURI(Uri.fromFile(new File(imagepath)));
                binding.etName.setText(dm2.getName());
                binding.etMobile.setText(dm2.getMobile());
                binding.etEmail.setText(dm2.getEmail());
            }
        }


        initView();

        return view;
    }

    private void initView() {


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.etName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "please  Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.etMobile.getText().toString().isEmpty() || binding.etMobile.getText().toString().length() < 10) {
                    Toast.makeText(getContext(), "please Enter Valid mobile no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!binding.etEmail.getText().toString().matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    Toast.makeText(getContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                DetailsModel detailsModel = new DetailsModel();
                detailsModel.setImage(imagepath);
                detailsModel.setName(binding.etName.getText().toString());
                detailsModel.setMobile(binding.etMobile.getText().toString());
                detailsModel.setEmail(binding.etEmail.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putParcelable("Basic Details", detailsModel);
                getParentFragmentManager().setFragmentResult("Basic Details", bundle);


                ((MainActivity) getActivity()).viewPager.setCurrentItem(1);


            }
        });
        binding.ivImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                builder.setCancelable(true);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                alertDialog = builder.show();

                TextView tvCamera = dialogView.findViewById(R.id.tvCamera);
                TextView tvGallery = dialogView.findViewById(R.id.tvGallery);
                AlertDialog finalAlertDialog = alertDialog;

                tvCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                            cameraResultLauncher.launch(intent);
                            finalAlertDialog.cancel();

                        } else {
                            Toast.makeText(getContext(), "NO data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                tvGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {

                            gallaryResultLauncher.launch(intent);
                            finalAlertDialog.cancel();

                        } else {
                            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}