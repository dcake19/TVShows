package com.example.android.tvshows.ui.find;

import android.support.annotation.Nullable;

import com.example.android.tvshows.data.db.ShowsRepository;
import com.example.android.tvshows.data.rest.ApiService;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class ResultsModule {

    private final ResultsFragment mResultsFragment;
    private final ResultsContract.View mView;

    public ResultsModule(ResultsFragment resultsFragment, ResultsContract.View view) {
        mResultsFragment = resultsFragment;
        mView = view;
    }

    @Provides
    @FragmentScoped
    public ResultsContract.Presenter provideResultsContractPresenter(ApiService service, ShowsRepository showsRepository){
            return new ResultsPresenter(mView, service, showsRepository);
    }

    @Provides
    @FragmentScoped
    public ResultsAdapter provideResultsAdapter(ResultsContract.Presenter presenter, Picasso picasso){
            return new ResultsAdapter(mResultsFragment.getActivity(), presenter, picasso);
    }

}
