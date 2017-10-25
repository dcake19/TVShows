package com.example.android.tvshows.ui.myshows.current;


import android.os.Parcel;
import android.os.Parcelable;

public class CurrentInfo {

    private String showName,episodeName,overview,posterUrl;

    public CurrentInfo(String showName, String episodeName, String overview, String posterUrl) {
        this.showName = showName;
        this.episodeName = episodeName;
        this.overview = overview;
        this.posterUrl = posterUrl;
    }

    public String getShowName() {
        return showName;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    @Override
    public boolean equals(Object obj) {
        CurrentInfo currentInfo = (CurrentInfo) obj;
        return this.showName.equals(currentInfo.getShowName()) && this.episodeName.equals(currentInfo.getEpisodeName())
                && this.overview.equals(currentInfo.getOverview()) && this.posterUrl.equals(currentInfo.getPosterUrl());
    }

}
