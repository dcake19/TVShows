package com.example.android.tvshows.ui.myshows.shows;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.android.tvshows.R;
import com.example.android.tvshows.ShowsApplication;
import com.example.android.tvshows.data.db.ShowsRepository;
import com.example.android.tvshows.ui.myshows.MyShowsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowsFragment extends Fragment implements ShowsContract.View{

    public static ShowsFragment getInstance(){
        ShowsFragment showsFragment = new ShowsFragment();
        return showsFragment;
    }

    @Inject ShowsAdapter mShowsAdapter;
    @Inject ShowsContract.Presenter mShowsPresenter;

    @BindView(R.id.recyclerview_shows) RecyclerView mRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowsApplication showsApplication = (ShowsApplication) getActivity().getApplication();

        ShowsComponent component = DaggerShowsComponent.builder()
                .applicationComponent(showsApplication.get(getActivity()).getComponent())
                .showsModule(showsApplication.getShowsModule(this, this))
                .build();

        component.inject(this);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.myshows_shows_fragment,container,false);

        ButterKnife.bind(this,rootview);

        setupRecyclerView();

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        mShowsPresenter.loadShowsFromDatabase(getActivity(),false,false);
    }

    private void setupRecyclerView(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int columns = (int) dpWidth/ 400;
        columns = columns>=1 ? columns:1;
        mRecyclerView.setAdapter(mShowsAdapter);
        StaggeredGridLayoutManager glm = new StaggeredGridLayoutManager(columns,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(glm);
    }

    @Override
    public void showsDataLoaded(int size) {
        mShowsAdapter.displayShows(size);
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            MyShowsActivity myShowsActivity = (MyShowsActivity) getActivity();
            if(myShowsActivity!=null) myShowsActivity.setFilterButtonVisible(true);
        }
    }
}
