package com.frunch.main.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seerasu1 on 10/10/16.
 */
public class MenuItemObject implements Parcelable {

    String name, descr, id, itemCategoryId, spiceLevel;
    double price, totalAmount;
    int quantity, selected;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(String itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public MenuItemObject() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(descr);
        dest.writeString(id);
        dest.writeString(itemCategoryId);
        dest.writeString(spiceLevel);
        dest.writeDouble(price);
        dest.writeDouble(totalAmount);
        dest.writeInt(quantity);
        dest.writeInt(selected);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public MenuItemObject createFromParcel(Parcel in) {
            return new MenuItemObject(in);
        }

        public MenuItemObject[] newArray(int size) {
            return new MenuItemObject[size];
        }
    };

    // "De-parcel object
    public MenuItemObject(Parcel in) {
        this.name = in.readString();
        this.descr = in.readString();
        this.id = in.readString();
        this.itemCategoryId = in.readString();
        this.spiceLevel = in.readString();
        this.price = in.readDouble();
        this.totalAmount = in.readDouble();
        this.quantity = in.readInt();
        this.selected = in.readInt();
    }
}
