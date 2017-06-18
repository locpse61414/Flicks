package com.tutorial.phant.flicks.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tutorial.phant.flicks.DetailActivity;
import com.tutorial.phant.flicks.FullScreenActivity;
import com.tutorial.phant.flicks.R;
import com.tutorial.phant.flicks.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phant on 16-Jun-17.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int POPULARVIEW = 0;
    private static final int NORMALVIEW = 1;
//    private static final int LANDSCAPEVIEW = 2;
    private static final String INTENT_MOVIE = "intentMovie";
    private Context context;
    private RecyclerView.ViewHolder viewHolder;
    private List<Movie> movies;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (Double.parseDouble(movies.get(position).getVote_average().trim()) >= 5) {
            return POPULARVIEW;
        } else {
            return NORMALVIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int orientation = parent.getResources().getConfiguration().orientation;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case POPULARVIEW:
                if(orientation == Configuration.ORIENTATION_PORTRAIT){
                    View view1 = inflater.inflate(R.layout.item_movie_1, parent, false);
                    viewHolder = new ViewHolder1(view1);
                }else{
                    View view3 = inflater.inflate(R.layout.item_movie_3, parent, false);
                    viewHolder = new ViewHolder3(view3);
                }

                break;
            default:
                View view2 = inflater.inflate(R.layout.item_movie_2, parent, false);
                viewHolder = new ViewHolder2(view2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        switch (holder.getItemViewType()) {
            case POPULARVIEW:
                int orientation = context.getResources().getConfiguration().orientation;
                if(orientation == Configuration.ORIENTATION_PORTRAIT){
                    ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                    configureViewHolder1(viewHolder1, movie);
                    break;
                }else{
                    ViewHolder3 viewHolder3 = (ViewHolder3) holder;
                    configureViewHolder3(viewHolder3, movie);
                    break;
                }

            default:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                configureViewHolder2(viewHolder2, movie);
                break;
        }
        if (position == movies.size() - 1 && onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore();
        }
    }

    private void configureViewHolder3(ViewHolder3 viewHolder3, final Movie movie) {
        Glide.with(context)
                .load(movie.getBackdrop_path())
                .placeholder(R.drawable.progress_animation)
                .crossFade()
                .centerCrop()
                .into(viewHolder3.ivMovie3);
        viewHolder3.tvTitle3.setText(movie.getTitle());
        viewHolder3.tvOverview3.setText(movie.getOverview());
        viewHolder3.ivPlayButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra(INTENT_MOVIE, movie);
                context.startActivity(intent);
            }
        });

    }

    private void configureViewHolder2(ViewHolder2 viewHolder2, final Movie movie) {
        int orientation = viewHolder2.itemView.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Glide.with(context)
                    .load(movie.getPoster_path())
                    .placeholder(R.drawable.progress_animation)
                    .crossFade()
                    .centerCrop()
                    .into(viewHolder2.ivMovie2);
        }else{
            Glide.with(context)
                    .load(movie.getBackdrop_path())
                    .placeholder(R.drawable.progress_animation)
                    .crossFade()
                    .centerCrop()
                    .into(viewHolder2.ivMovie2);
        }
        viewHolder2.tvTitle.setText(movie.getTitle());
        viewHolder2.tvOverview.setText(movie.getOverview());


        viewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(INTENT_MOVIE, movie);
                context.startActivity(intent);
            }
        });
    }

    private void configureViewHolder1(final ViewHolder1 viewHolder1, final Movie movie) {
        Glide.with(context)
                .load(movie.getBackdrop_path())
                .placeholder(R.drawable.progress_animation)
                .crossFade()
                .centerCrop()
                .into(viewHolder1.ivMovie1);
        viewHolder1.ivPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra(INTENT_MOVIE, movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> list) {
        movies.clear();
        movies.addAll(list);
        notifyDataSetChanged();
    }

    public void addMore(List<Movie> list) {
        movies.addAll(list);
        notifyItemRangeChanged(movies.size(), list.size());
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.ivMovie1)
        public ImageView ivMovie1;
        @BindView(R.id.ivPlayButton)
        public ImageView ivPlayButton;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.ivMovie2)
        public ImageView ivMovie2;
        @BindView(R.id.tvTitle)
        public TextView tvTitle;
        @BindView(R.id.tvOverview)
        public TextView tvOverview;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        @BindView(R.id.ivMovie3)
        public ImageView ivMovie3;
        @BindView(R.id.tvTitle3)
        public TextView tvTitle3;
        @BindView(R.id.tvOverview3)
        public TextView tvOverview3;
        @BindView(R.id.ivPlayButton3)
        public ImageView ivPlayButton3;

        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
