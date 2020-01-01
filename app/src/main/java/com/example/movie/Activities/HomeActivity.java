package com.example.movie.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.movie.Classes.Movie;
import com.example.movie.Classes.Results;
import com.example.movie.Adapters.HomePageAdapter;
import com.example.movie.Apis.JsonPlaceHolderApi;
import com.example.movie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView rw;
    private ArrayList<Movie> list = new ArrayList<>();
    private HomePageAdapter adapter;
    private Results results;
    private Results pageResults;
    private Integer currentPage = 0, totalPages = 0;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getTotalNumberOfPages(new FirebaseCallback() {
            @Override
            public void onCallback(Integer pageCount) {

                pagination(pageCount);
            }
        });

    }

    public void getTotalNumberOfPages(final FirebaseCallback callback)
    {
        Call<Results> callPageInfo = jsonPlaceHolderApi.movieList1("171a171502ad0861e569dd52245b893a");
        callPageInfo.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {

                pageResults = response.body();
                currentPage = pageResults.getPage();
                totalPages = pageResults.getTotal_pages();
                callback.onCallback(totalPages);

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });

    }

    public void pagination(int totalPages)
    {
        for(int j=1;j<=totalPages;++j)
        {
            Call<Results> call = jsonPlaceHolderApi.movieList("171a171502ad0861e569dd52245b893a",j);

            rw = findViewById(R.id.recview);
            rw.setLayoutManager(new LinearLayoutManager(this));
            adapter = new HomePageAdapter(HomeActivity.this, list);
            rw.setAdapter(adapter);

            call.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {

                    results = response.body();

                    for (int i=0; i< results.getResults().size(); ++i)
                    {
                        String path = results.getResults().get(i).getPoster_path();
                        results.getResults().get(i).setPoster_path("https://image.tmdb.org/t/p/w500//" + path);
                        list.add(results.getResults().get(i));
                        counter++;
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Log.d("HomeActivity", t.getMessage());
                }
            });
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        ArrayList<Movie> newList = new ArrayList<>();

        for(Movie movie : list)
        {
            if(movie.getOriginal_title().toLowerCase().contains(userInput))
            {
                newList.add(movie);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    public interface FirebaseCallback{
        void onCallback(Integer pageCount);   // custom callback that waits for the data
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }
}
