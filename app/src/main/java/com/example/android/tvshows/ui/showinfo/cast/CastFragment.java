package com.example.android.tvshows.ui.showinfo.cast;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CastFragment extends Fragment implements CastContract.View{

    private static int mTmdbId;

    public static CastFragment getInstance(int tmdbId){
        mTmdbId = tmdbId;
        return new CastFragment();
    }

    @BindView(R.id.recyclerview_cast) RecyclerView mRecyclerView;

    @Inject CastAdapter mCastAdapter;
    @Inject CastContract.Presenter mCastPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowsApplication showsApplication = (ShowsApplication) getActivity().getApplication();

        CastComponent component = DaggerCastComponent.builder()
                .applicationComponent(showsApplication.get(getActivity()).getComponent())
                .castModule(showsApplication.getCastModule(this, this, mTmdbId))
                .build();

        component.inject(this);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.show_info_cast_fragment,container,false);
        ButterKnife.bind(this,rootview);

        setupRecyclerView();

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCastPresenter.loadCastData(getActivity());
    }

    private void setupRecyclerView(){
        mRecyclerView.setAdapter(mCastAdapter);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),1);
        mRecyclerView.setLayoutManager(glm);
    }

    @Override
    public void castDataLoaded(int size) {
        mCastAdapter.displayCast(size);
    }

}
