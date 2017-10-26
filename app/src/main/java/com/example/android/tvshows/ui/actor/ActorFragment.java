package com.example.android.tvshows.ui.actor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.tvshows.R;
import com.example.android.tvshows.ShowsApplication;
import com.example.android.tvshows.data.db.ShowsDbContract;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActorFragment extends Fragment implements ActorContract.View{

    @BindView(R.id.actor_photo)ImageView mPhoto;
    @BindView(R.id.actor_biography)TextView mBiography;
    @BindView(R.id.title_biography)TextView mTitleBiography;
    @BindView(R.id.title_tv_credits)TextView mTitleTvCredits;
    @BindView(R.id.loading_indicator)ProgressBar mProgressBar;
    @BindView(R.id.recyclerview_actor)RecyclerView mRecyclerView;

    @Inject
    ActorContract.Presenter mActorPresenter;
    @Inject
    Picasso mPicasso;
    @Inject ActorAdapter mActorAdapter;

    private int mTmdbActorId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mTmdbActorId = intent.getIntExtra(ShowsDbContract.CastEntry.COLUMN_PERSON_ID, -1);

        ShowsApplication showsApplication = (ShowsApplication) getActivity().getApplication();

        ActorComponent component = DaggerActorComponent.builder()
                .applicationComponent(showsApplication.get(getActivity()).getComponent())
                .actorModule(showsApplication.getActorModule((ActorActivity)getActivity(), this, mTmdbActorId))
                .build();
        component.inject(this);

        setRetainInstance(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.actor_fragment, container, false);
        ButterKnife.bind(this,rootview);

        setupRecyclerView();

        return  rootview;
    }


    private void setupRecyclerView(){
        mRecyclerView.setAdapter(mActorAdapter);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),1);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mActorPresenter.downloadActorData(getContext());
    }

    @Override
    public void setImage(String url) {
        mPicasso.load(getString(R.string.poster_path)+url).into(mPhoto);
    }

    @Override
    public void setName(String name) {
        mProgressBar.setVisibility(View.INVISIBLE);
        ((ActorActivity)getActivity()).mName.setText(name);
    }

    @Override
    public void setBiography(String biography) {
        mTitleBiography.setVisibility(View.VISIBLE);
        mBiography.setText(biography);
    }

    @Override
    public void displayCredits(int size) {
        mTitleTvCredits.setVisibility(View.VISIBLE);
        mActorAdapter.displayCredits(size);
    }

    @Override
    public void noConnection() {
        mProgressBar.setVisibility(View.INVISIBLE);
        ((ActorActivity)getActivity()).noConnection();
    }

    @Override
    public void startingDownload() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

}
