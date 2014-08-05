package com.connerpike.boxofficemovies.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.connerpike.boxofficemovies.Adapters.BoxOfficeMovieAdapter;
import com.connerpike.boxofficemovies.Model.BoxOfficeMovie;
import com.connerpike.boxofficemovies.Model.RottenTomatoesClient;
import com.connerpike.boxofficemovies.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BoxOfficeActivity extends Activity {
    private ListView lvMovies;
    private BoxOfficeMovieAdapter movieAdapter;

    private RottenTomatoesClient client;


    public static final String MOVIE_DETAIL_KEY = "selected_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movieAdapter = new BoxOfficeMovieAdapter(this, new ArrayList<BoxOfficeMovie>());
        lvMovies.setAdapter(movieAdapter);


        fetchBoxOfficeMovies();

        setupMovieSelectedListener();

    }

    private void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BoxOfficeActivity.this, BoxOfficeDetailActivity.class);
                intent.putExtra(MOVIE_DETAIL_KEY, movieAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void fetchBoxOfficeMovies() {
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, JSONObject jsonObject) {
                JSONArray items = null;
                try {
                    items = jsonObject.getJSONArray("movies");

                    ArrayList<BoxOfficeMovie> boxOfficeMovies = BoxOfficeMovie.fromJson(items);
                    movieAdapter.clear();

                    for (BoxOfficeMovie m : boxOfficeMovies) {
                        movieAdapter.add(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Movies Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.box_office, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(getClass().getName(), item.getTitle() + " item pushed");

        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_refresh:
                fetchBoxOfficeMovies();
                break;
            case R.id.action_clear_all_movies:
                clearAllMovies();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearAllMovies() {
        movieAdapter.clear();
        Toast.makeText(getApplicationContext(), "Movies Cleared", Toast.LENGTH_SHORT).show();
    }
}
