package com.tutorial.phant.flicks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tutorial.phant.flicks.adapter.MovieAdapter;
import com.tutorial.phant.flicks.api.MovieApi;
import com.tutorial.phant.flicks.model.NowPlaying;
import com.tutorial.phant.flicks.utils.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;
//    @BindView(R.id.relateLayout)
//    RelativeLayout relateLayout;

    private MovieAdapter movieAdapter;
    private MovieApi movieApi;
    private static int CURRENTPAGE = -1;
    private static final int FIRSTPAGE = 1;
    private static int NEXTTPAGE = 1;

    private interface Listener {
        void onResult(NowPlaying nowPlaying);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupApi();
        setupView();
        loadFirstTime();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NEXTTPAGE = 1;
                loadFirstTime();
                swipeContainer.setRefreshing(false);
            }
        });

        movieAdapter.setOnLoadMoreListener(new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

    }

    private void setupApi() {
        Retrofit retrofit = RetrofitUtils.create();
        movieApi = retrofit.create(MovieApi.class);
    }

    private void loadFirstTime() {
        CURRENTPAGE = FIRSTPAGE;
        fetchMovies(new Listener() {
            @Override
            public void onResult(NowPlaying nowPlaying) {
                movieAdapter.setMovies(nowPlaying.getMovies());
            }
        });
    }

    ;

    private void loadMore() {
        NEXTTPAGE = NEXTTPAGE + 1;
        CURRENTPAGE = NEXTTPAGE;
        fetchMovies(new Listener() {
            @Override
            public void onResult(NowPlaying nowPlaying) {
                if (nowPlaying.getMovies() != null) {
                    movieAdapter.addMore(nowPlaying.getMovies());
                }
            }
        });
    }

    ;

    private void fetchMovies(final Listener listener) {
        movieApi.nowPlaying(CURRENTPAGE).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Log.d("CoderSchool", response.toString());
//                relateLayout.setVisibility(View.GONE);
                listener.onResult(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });
    }


    private void setupView() {
        movieAdapter = new MovieAdapter();
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setAdapter(movieAdapter);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}