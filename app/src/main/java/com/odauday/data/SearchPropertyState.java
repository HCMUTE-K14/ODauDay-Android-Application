package com.odauday.data;

/**
 * Created by infamouSs on 4/14/18.
 */
public enum SearchPropertyState {
    RUNNING(true),
    COMPLETE_DOWNLOAD_DATA_FROM_SERVICE(true),
    COMPLETE_SHOW_DATA(false),
    ERROR(false);
    
    private boolean isShouldShowProgressBar;
    
    SearchPropertyState(boolean isShouldShowProgressBar) {
        this.isShouldShowProgressBar = isShouldShowProgressBar;
    }
    
    public boolean isShouldShowProgressBar() {
        return isShouldShowProgressBar;
    }
}
