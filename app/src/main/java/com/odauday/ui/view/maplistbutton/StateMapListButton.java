package com.odauday.ui.view.maplistbutton;

/**
 * Created by infamouSs on 3/31/18.
 */

public enum StateMapListButton {
    SHOWING_MAP_VIEW(0),
    SHOWING_LIST_VIEW(1);

    private final int state;

    StateMapListButton(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
