package com.example.android.tvshows.ui.actor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.tvshows.R;
import com.example.android.tvshows.ShowsApplication;
import com.example.android.tvshows.data.db.ShowsDbContract;
import com.example.android.tvshows.data.model.Actor;
import com.example.android.tvshows.data.model.ExternalIds;
import com.example.android.tvshows.data.model.actortvcredits.ActorTVCredits;
import com.example.android.tvshows.util.ExternalLinks;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActorActivity extends AppCompatActivity{// implements ActorContract.View{

    private final String FRAGMENT = "Actor Fragment";
    private ActorFragment mFragment;

    @BindView(R.id.actor_name) TextView mName;
    @BindView(R.id.actor_layout)View mActorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actor_activity);

        getWindow().setBackgroundDrawable(null);

        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        mFragment = (ActorFragment) fm.findFragmentByTag(FRAGMENT);

        if(mFragment==null) {
            mFragment = new ActorFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.add(R.id.actor_content, mFragment, FRAGMENT);
            fragmentTransaction.commit();
        }

        setupToolbar();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.links_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_links:
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.actor_link_menu, null);
                TextView imdb = (TextView) view.findViewById(R.id.link_imdb);
                imdb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ExternalLinks.visitIMDBActorPage(getBaseContext(),mFragment.mActorPresenter.getActorIMDBId());
                    }
                });
                PopupWindow linksPopupWindow = new PopupWindow(
                        view,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                linksPopupWindow.setOutsideTouchable(true);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int height = metrics.heightPixels;
                linksPopupWindow.showAtLocation(mActorLayout, Gravity.RIGHT,0,-(height/2 + 50));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void noConnection() {
        Snackbar.make(mActorLayout, getResources().getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry),
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                mFragment.mProgressBar.setVisibility(View.VISIBLE);
                                mFragment.mActorPresenter.downloadActorData(getBaseContext());
                            }
                        }
                )
                .show();
    }

    public static Intent getIntent(Context context,int personId){
        Intent intent = new Intent(context, ActorActivity.class);
        intent.putExtra(ShowsDbContract.CastEntry.COLUMN_PERSON_ID,personId);
        return intent;
    }

}
