package com.example.android.tvshows.ui.myshows.current;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tvshows.R;
import com.example.android.tvshows.ShowsApplication;
import com.example.android.tvshows.data.db.ShowsRepository;
import com.example.android.tvshows.ui.myshows.MyShowsActivity;
import com.example.android.tvshows.ui.myshows.shows.DaggerShowsComponent;
import com.example.android.tvshows.ui.myshows.shows.ShowsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentFragment extends Fragment implements CurrentContract.View{

    private static int mCurrentType = 1;

    public static CurrentFragment getInstance(int currentType){
        CurrentFragment currentFragment = new CurrentFragment();
        mCurrentType = currentType;
        return currentFragment;
    }

    @BindView(R.id.recyclerview_current) RecyclerView mRecyclerView;

    @Inject CurrentAdapter mCurrentAdapter;
    @Inject CurrentContract.Presenter mCurrentPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowsApplication showsApplication = (ShowsApplication) getActivity().getApplication();

        CurrentComponent component = DaggerCurrentComponent.builder()
                .applicationComponent(showsApplication.get(getActivity()).getComponent())
                .currentModule(showsApplication.getCurrentModule(this, this, mCurrentType))
                .build();

        component.inject(this);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.myshows_current_fragment,container,false);
        ButterKnife.bind(this,layout);

        mRecyclerView.setAdapter(mCurrentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }


    @Override
    public void onStart() {
        super.onStart();
        mCurrentPresenter.loadShowsFromDatabase(getActivity());
    }

    @Override
    public void showsDataLoaded(int size) {
        mCurrentAdapter.displayShows(size);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            MyShowsActivity myShowsActivity = (MyShowsActivity) getActivity();
            if(myShowsActivity!=null) myShowsActivity.setFilterButtonVisible(false);
        }
    }
}
