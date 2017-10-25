package com.example.android.tvshows.ui.showinfo.seasons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tvshows.R;
import com.example.android.tvshows.ShowsApplication;
import com.example.android.tvshows.data.db.ShowsRepository;
import com.example.android.tvshows.ui.myshows.shows.DaggerShowsComponent;
import com.example.android.tvshows.ui.myshows.shows.ShowsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonsFragment extends Fragment implements SeasonsContract.View{

    private static int mTmdbId;

    public static SeasonsFragment getInstance(int tmdbId){
        mTmdbId = tmdbId;
        return new SeasonsFragment();
    }

    @Inject SeasonsAdapter mSeasonsAdapter;
    @Inject SeasonsContract.Presenter mSeasonsPresenter;

    @BindView(R.id.recyclerview_seasons) RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowsApplication showsApplication = (ShowsApplication) getActivity().getApplication();

        SeasonsComponent component = DaggerSeasonsComponent.builder()
                .applicationComponent(showsApplication.get(getActivity()).getComponent())
                .seasonsModule(showsApplication.getSeasonsModule(this, this, mTmdbId))
                .build();

        component.inject(this);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.show_info_seasons_fragment,container,false);
        ButterKnife.bind(this,rootview);

        setupRecyclerView();

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSeasonsPresenter.loadSeasonsData(getActivity());
    }

    private void setupRecyclerView(){
        mRecyclerView.setAdapter(mSeasonsAdapter);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),1);
        mRecyclerView.setLayoutManager(glm);

    }

    @Override
    public void seasonDataLoaded(int size) {
        mSeasonsAdapter.displaySeasons(size);
    }


}
