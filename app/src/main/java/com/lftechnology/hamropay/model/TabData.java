package com.lftechnology.hamropay.model;

/**
 * Created by laaptu on 3/18/16.
 */
public class TabData {

    public int selectedDrawableId, unselectedDrawableId;
    public String title;
    public boolean selected = false;

    public TabData() {

    }

    public TabData(String title, boolean selected, int... drawableId) {
        this.title = title;
        this.selected = selected;
        this.selectedDrawableId = drawableId[0];
        this.unselectedDrawableId = drawableId[1];
    }
}
