package com.tentenlabs.frunch.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by seerasu1 on 10/10/16.
 */
public class ItemMenuCategory implements Serializable{

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
}
