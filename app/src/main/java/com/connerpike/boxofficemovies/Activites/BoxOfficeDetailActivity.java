package com.connerpike.boxofficemovies.Activites;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.connerpike.boxofficemovies.Model.BoxOfficeMovie;
import com.connerpike.boxofficemovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by connerpike on 8/3/14.
 */
public class BoxOfficeDetailActivity extends Activity {
    private BoxOfficeMovie movie;
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private TextView tvCriticsConsensus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_detail);
        // Fetch views
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvCriticsConsensus = (TextView) findViewById(R.id.tvCriticsConsensus);
        tvAudienceScore = (TextView) findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);
        // Use the movie to populate the data into our views
        BoxOfficeMovie movie = (BoxOfficeMovie)
                getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    private void loadMovie(BoxOfficeMovie movie) {
        this.movie = movie;
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText(Html.fromHtml("<b>Critics Score:</b> " + movie.getCriticsScore() + "%"));
        tvAudienceScore.setText(Html.fromHtml("<b>Audience Score:</b> " + movie.getAudienceScore() + "%"));
        tvCast.setText(movie.getCastList());
        tvSynopsis.setText(Html.fromHtml("<b>Synopsis:</b> " + movie.getSynopsis()));
        tvCriticsConsensus.setText(Html.fromHtml("<b>Consensus:</b> " + movie.getCriticsConsensus()));
        // R.drawable.large_movie_poster from
        // http://content8.flixster.com/movie/11/15/86/11158674_pro.jpg -->
        Picasso.with(this).load(movie.getLargePosterUrl()).
                placeholder(R.drawable.large_movie_poster).
                into(ivPosterImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.box_office_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_external_link_rotten_tomatoes:
                openRottenTomatoesLink();
                break;
            case R.id.action_external_link_imdb:
                openIMDBLink();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openIMDBLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getIMDBLink()));
        startActivity(intent);
    }

    private void openRottenTomatoesLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getRottenTomatoesLink()));
        startActivity(intent);
    }
}