package com.tentenlabs.frunch.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by seerasu1 on 10/10/16.
 */
public class ItemMenuCategory implements Parcelable{

    String id, name, descr;
    List<MenuItemObject> menuItemObjectList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<MenuItemObject> getMenuItemObjectList() {
        return menuItemObjectList;
    }

    public void setMenuItemObjectList(List<MenuItemObject> menuItemObjectList) {
        this.menuItemObjectList = menuItemObjectList;
    }

    public ItemMenuCategory() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(descr);
        dest.writeString(id);
        dest.writeList(menuItemObjectList);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public ItemMenuCategory createFromParcel(Parcel in) {
            return new ItemMenuCategory(in);
        }

        public ItemMenuCategory[] newArray(int size) {
            return new ItemMenuCategory[size];
        }
    };

    // "De-parcel object
    public ItemMenuCategory(Parcel in) {
        this.name = in.readString();
        this.descr = in.readString();
        this.id = in.readString();
        this.menuItemObjectList = in.readArrayList(null);
    }
}
