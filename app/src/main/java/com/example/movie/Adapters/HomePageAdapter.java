package com.example.movie.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.Classes.Movie;
import com.example.movie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public HomePageAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_movies,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(movies.get(position).getOriginal_title());
        Picasso.get().load(movies.get(position).getPoster_path()).into(holder.movieImage);

        holder.description.setText(movies.get(position).getOverview());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description;
        private ImageView movieImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            description = (TextView) itemView.findViewById(R.id.textViewDescription);
            movieImage = (ImageView) itemView.findViewById(R.id.moviePic);
        }
    }

    public void updateList(ArrayList<Movie> newMovieList)
    {
        movies = new ArrayList<>();
        movies.addAll(newMovieList);
        notifyDataSetChanged();
    }
}
