package com.frunch.main.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seerasu1 on 09/10/16.
 */
public class RestarantObject implements Parcelable {

    String name, description, phone, id, cuisineId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
    }

    public RestarantObject() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(phone);
        dest.writeString(id);
        dest.writeString(cuisineId);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public RestarantObject createFromParcel(Parcel in) {
            return new RestarantObject(in);
        }

        public RestarantObject[] newArray(int size) {
            return new RestarantObject[size];
        }
    };

    // "De-parcel object
    public RestarantObject(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.phone = in.readString();
        this.id = in.readString();
        this.cuisineId = in.readString();
    }
}
