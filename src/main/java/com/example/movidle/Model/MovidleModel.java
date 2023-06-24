package com.example.movidle.Model;
import com.example.movidle.Exceptions.DataAccessException;
import com.example.movidle.Helpers.DataAccess;
import com.example.movidle.Helpers.Movie;


import java.util.ArrayList;
import java.util.List;

public class MovidleModel {
    private List<Movie> movies;

    public MovidleModel(DataAccess dataAccess) {
        movies = new ArrayList<>();
    }


    public void loadMoviesFromCSV() throws DataAccessException {
        movies = DataAccess.readMoviesFromCSV();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Movie> searchMoviesByTitle(String searchText) {
        List<Movie> matchingMovies = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().startsWith(searchText.toLowerCase())) {
                matchingMovies.add(movie);
            }
        }

        return matchingMovies;
    }
    private static MovidleModel instance;

    // Diğer özellikler ve metotlar

    private MovidleModel() {
        // Gizli kurucu metot
    }

    public static synchronized MovidleModel getInstance() {
        if (instance == null) {
            instance = new MovidleModel();
        }
        return instance;
    }
}
