package com.example.fragmentviewpager.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity
public class DetailsModel implements Parcelable {
    @PrimaryKey
    int id;
    String image;
    String name;
    String mobile;
    String alternateMobile;
    String email;
    String address;
    String pinCode;
    String highestQualification;
    String university;
    String college;
    String percentage;

    public DetailsModel(int id, String image, String name, String mobile, String alternateMobile, String email, String address, String pinCode, String highestQualification, String university, String college, String percentage) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.mobile = mobile;
        this.alternateMobile = alternateMobile;
        this.email = email;
        this.address = address;
        this.pinCode = pinCode;
        this.highestQualification = highestQualification;
        this.university = university;
        this.college = college;
        this.percentage = percentage;
    }

    public DetailsModel() {

    }


    protected DetailsModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image=in.readString();
        mobile = in.readString();
        alternateMobile = in.readString();
        email = in.readString();
        address = in.readString();
        pinCode = in.readString();
        highestQualification = in.readString();
        university = in.readString();
        college = in.readString();
        percentage = in.readString();
    }

    public static final Creator<DetailsModel> CREATOR = new Creator<DetailsModel>() {
        @Override
        public DetailsModel createFromParcel(Parcel in) {
            return new DetailsModel(in);
        }

        @Override
        public DetailsModel[] newArray(int size) {
            return new DetailsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "DetailsModel{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", alternateMobile='" + alternateMobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", highestQualification='" + highestQualification + '\'' +
                ", university='" + university + '\'' +
                ", college='" + college + '\'' +
                ", percentage='" + percentage + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(mobile);
        parcel.writeString(alternateMobile);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(pinCode);
        parcel.writeString(highestQualification);
        parcel.writeString(university);
        parcel.writeString(college);
        parcel.writeString(percentage);
    }
}
