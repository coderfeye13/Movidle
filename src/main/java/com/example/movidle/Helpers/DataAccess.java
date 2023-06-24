package com.example.movidle.Helpers;

import com.example.movidle.Exceptions.DataAccessException;
import com.example.movidle.Helpers.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {
    private static final String CSV_FILE_PATH = "Assets/imdb_top_250.csv";

    public static List<Movie> readMoviesFromCSV() throws DataAccessException {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 8) {
                    String title = data[1];
                    int year = Integer.parseInt(data[2]);
                    String genre = data[3];
                    String origin = data[4];
                    String director = data[5];
                    String star = data[6];
                    String imdbLink = data[7];

                    Movie movie = new Movie(title, year, genre, origin, director, star, imdbLink);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            throw new DataAccessException("Veri okuma hatası: " + e.getMessage());
        }

        return movies;
    }
}
