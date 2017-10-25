package com.example.android.tvshows.ui.myshows.shows;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.tvshows.R;

public class ShowInfo {
    private int id;
    private String title,posterUrl,numberOfSeasons,numberOfEpisodes,inProduction;
    boolean favorite;

    public ShowInfo(Context context, int id, String title, String posterPath, Integer numberOfSeasons, Integer numberOfEpisodes, int inProduction,int favorite) {
        this.id = id;
        this.title = title;
        this.posterUrl = context.getString(R.string.poster_path) + posterPath;
        this.numberOfSeasons = numberOfSeasons.toString();
        this.numberOfEpisodes = numberOfEpisodes.toString();
        this.inProduction = inProduction==1 ? context.getString(R.string.continuing) : context.getString(R.string.finished);
        this.favorite = favorite==1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public String getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public String getInProduction() {
        return inProduction;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
