package com.example.android.tvshows.ui.showinfo.seasons;

import com.example.android.tvshows.util.Utility;

public class SeasonInfo {
    public String seasonName;
    public String airDate;
    public String posterUrl;
    public String overview;
    public String numberOfEpisodes;
    public int seasonNumber;

    public SeasonInfo(String seasonName,Integer day ,Integer month,Integer year, String posterUrl, String overview, Integer numberOfEpisodes, int seasonNumber) {
        this.seasonName = seasonName;
        this.airDate = Utility.getDateAsString(day,month,year);
        this.posterUrl = posterUrl;
        this.overview = overview;
        this.numberOfEpisodes = numberOfEpisodes.toString();
        this.seasonNumber = seasonNumber;
    }

}
