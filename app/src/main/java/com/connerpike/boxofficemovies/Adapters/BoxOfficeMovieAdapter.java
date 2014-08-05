package com.connerpike.boxofficemovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.connerpike.boxofficemovies.Model.BoxOfficeMovie;
import com.connerpike.boxofficemovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by connerpike on 8/2/14.
 */
public class BoxOfficeMovieAdapter extends ArrayAdapter<BoxOfficeMovie> {


    public BoxOfficeMovieAdapter(Context context, ArrayList<BoxOfficeMovie> movies) {
        super(context, 0, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BoxOfficeMovie movie = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_box_office_movie, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView criticsRating = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView cast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivPosterImage);

        title.setText(movie.getTitle());
        criticsRating.setText("Score: " + movie.getCriticsScore() + "%");
        cast.setText(movie.getCastList());

        Picasso.with(getContext()).load(movie.getPosterUrl()).into(imageView);

        return convertView;

    }
}
