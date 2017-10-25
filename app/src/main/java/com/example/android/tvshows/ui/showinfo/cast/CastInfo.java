package com.example.android.tvshows.ui.showinfo.cast;

import android.os.Parcel;
import android.os.Parcelable;

public class CastInfo {
    private String characterName, actorName, photoUrl;
    private int personId;

    public CastInfo(String characterName, String actorName, String photoUrl,int personId) {
        this.characterName = characterName;
        this.actorName = actorName;
        this.photoUrl = photoUrl;
        this.personId = personId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getActorName() {
        return actorName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getPersonId() {
        return personId;
    }

}