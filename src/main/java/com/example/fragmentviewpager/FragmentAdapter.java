package com.example.fragmentviewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.fragmentviewpager.Fragments.EducationDetailsFragment;
import com.example.fragmentviewpager.Fragments.BasicDetailsFragment;
import com.example.fragmentviewpager.Fragments.ContactDetailsFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){


            case 0:return new BasicDetailsFragment();

            case 1:return new ContactDetailsFragment();

            case 2:return new EducationDetailsFragment();

            default:return  new BasicDetailsFragment();

        }
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;

        if (position==0){
            title="Basic"+"\n"+ "Details";
        }else if (position==1){
            title="Contact Details";
        }else if (position==2){
            title="Education Details";
        }
        return title;
    }
}
