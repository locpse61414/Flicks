package com.tutorial.phant.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.tutorial.phant.flicks.api.TrailerApi;
import com.tutorial.phant.flicks.model.Movie;
import com.tutorial.phant.flicks.model.Trailer;
import com.tutorial.phant.flicks.model.Trailers;
import com.tutorial.phant.flicks.utils.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle2)
    TextView tvTitle2;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @BindView(R.id.tvOverview2)
    TextView tvOverview2;
    @BindView(R.id.rbRate)
    RatingBar rbRate;

    private static final String INTENT_MOVIE = "intentMovie";
    private static final String NAME_TRAILER = "Official Trailer";
    private static String ID_MOVIE = "";
    private YouTubePlayerFragment youtubeFragment;
    private Movie movie;
    private TrailerApi trailerApi;
    private Trailer trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra(INTENT_MOVIE);
        ID_MOVIE = movie.getId();

        setupView();

        Retrofit retrofit = RetrofitUtils.create();
        trailerApi = retrofit.create(TrailerApi.class);
        trailerApi.trailers(ID_MOVIE).enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                Log.d("Trailers", response.toString());
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {

            }
        });
    }

    private void setupView() {
        tvTitle2.setText(movie.getTitle());
        tvReleaseDate.setText("Release Date: " + movie.getRelease_date());
        tvOverview2.setText(movie.getOverview());
        rbRate.setMax(10);
        rbRate.setStepSize(10.0f);
        rbRate.setNumStars(5);
        rbRate.setRating((float) (Double.parseDouble(movie.getVote_average()) / 2));
    }

    private void youtubeApi() {
        youtubeFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(trailer.getSource());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getParent(), "Can't found video", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleResponse(Response<Trailers> response) {
        try {
            Trailer temptTrailer = response.body().getTrailers().get(0);
        } catch (Exception e) {
            Toast.makeText(this, "Can't not found Trailer!!!!!!", Toast.LENGTH_LONG).show();
            return;
        }
        this.trailer = new Trailer(
                response.body().getTrailers().get(0).getName(),
                response.body().getTrailers().get(0).getSize(),
                response.body().getTrailers().get(0).getSource(),
                response.body().getTrailers().get(0).getType()
        );
        for (Trailer trailer : response.body().getTrailers()
                ) {
            if (trailer.getName().equalsIgnoreCase(NAME_TRAILER)) {
                this.trailer = new Trailer(trailer.getName(), trailer.getSize(), trailer.getSource(), trailer.getType());
                break;
            }
        }
        youtubeApi();
    }
}
