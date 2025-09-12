package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.MovieResponseDto;
import com.example.Movie.Reservation.dto.OmdbMovieDto;
import com.example.Movie.Reservation.dto.TmdbApiResponse;
import com.example.Movie.Reservation.dto.TmdbMovieDto;
import com.example.Movie.Reservation.model.Genre;
import com.example.Movie.Reservation.model.Movie;
import com.example.Movie.Reservation.repository.GenreRepository;
import com.example.Movie.Reservation.repository.MovieRepository;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TmdbService tmdbService;

    //Ombd
//    private OmdbService omdbService;
//    @Transactional
//    public Movie importMovie(String imdbId) {
//        if(movieRepository.findByImdbId(imdbId).isPresent()) {
//            throw new RuntimeException("Movie already exists");
//
//        }
//        OmdbMovieDto omdbMovie = omdbService.fetchMovieDetails(imdbId);
//        if (omdbMovie == null || omdbMovie.getTitle() == null) {
//            throw new RuntimeException("Movie not found in OMDB");
//        }
//        Movie movie = new Movie();
//        movie.setImdbId(omdbMovie.getImdbId());
//        movie.setTitle(omdbMovie.getTitle());
//        movie.setDescription(omdbMovie.getDescription());
//        movie.setPosterUrl(omdbMovie.getPosterUrl());
//        try{
//            int duration = Integer.parseInt(omdbMovie.getRuntime().replaceAll("[^\\d]", ""));
//            movie.setDuration(duration);
//        }catch (NumberFormatException e) {
//            movie.setDuration(0);
//        }
//        Set<Genre> genres = new HashSet<>();
//        if (omdbMovie.getGenre() != null) {
//            String[] genreNames = omdbMovie.getGenre().split(",\\s*");
//            for (String name : genreNames) {
//                Genre genre = genreRepository.findByName(name)
//                        .orElseGet(() -> {
//                            Genre newGenre = new Genre();
//                            newGenre.setName(name);
//                            return genreRepository.save(newGenre);
//                        });
//                genres.add(genre);
//            }
//        }
//        movie.setGenres(genres);
//        return movieRepository.save(movie);
//
//    }
//    public List<MovieResponseDto> getAllMovies(){
//        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//    public MovieResponseDto getMovieById(String uuid){
//        UUID movieeUuid = UUID.fromString(uuid);
//        return movieRepository.findById(movieeUuid).map(this::convertToDto).orElseThrow(()->new RuntimeException("Movie not found"));
//    }
//    private MovieResponseDto convertToDto(Movie movie){
//        MovieResponseDto dto = new MovieResponseDto();
//        dto.setId(movie.getId());
//        dto.setTitle(movie.getTitle());
//        dto.setDescription(movie.getDescription());
//        dto.setPosterUrl(movie.getPosterUrl());
//        dto.setDuration(movie.getDuration());
//        dto.setGenres(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
//        return dto;
//    }

//    Tmdb
public List<MovieResponseDto> getAllMovies() {
    return movieRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}
    public Optional<MovieResponseDto> getMovieById(Long id) {
        return movieRepository.findById(id).map(this::convertToDto);
    }
    @Transactional
    public List<Movie> importPopularMovies() {
        TmdbApiResponse popularMoviesResponse = tmdbService.getPopularMovies();
        Map<Integer, String> genreMap = tmdbService.getGenreMap();
        List<TmdbMovieDto> tmdbMovies = popularMoviesResponse.getResults();

        return tmdbMovies.stream()
                .map(tmdbMovie -> movieRepository.findById(tmdbMovie.getId())
                        .orElseGet(() -> createAndSaveMovie(tmdbMovie, genreMap)))
                .collect(Collectors.toList());
    }
    private Movie createAndSaveMovie(TmdbMovieDto tmdbMovie, Map<Integer, String> genreMap) {
        Movie movie = new Movie();
        movie.setId(tmdbMovie.getId());
        movie.setTitle(tmdbMovie.getTitle());
        movie.setDescription(tmdbMovie.getDescription());
        movie.setPosterUrl("https://image.tmdb.org/t/p/w500" + tmdbMovie.getPosterPath());

        TmdbMovieDto movieDetails = tmdbService.getMovieDetails(tmdbMovie.getId());

        if (movieDetails != null && movieDetails.getDuration() != null) {
            movie.setDuration(movieDetails.getDuration());
        } else {
            movie.setDuration(0);
        }

        Set<Genre> genres = new HashSet<>();
        if (tmdbMovie.getGenreIds() != null) {
            for (Integer genreId : tmdbMovie.getGenreIds()) {
                String genreName = genreMap.get(genreId);
                if (genreName != null) {
                    Genre genre = genreRepository.findByName(genreName)
                            .orElseGet(() -> {
                                Genre newGenre = new Genre();
                                newGenre.setName(genreName);
                                return genreRepository.save(newGenre);
                            });
                    genres.add(genre);
                }
            }
        }
        movie.setGenres(genres);

        return movieRepository.save(movie);
    }
    private MovieResponseDto convertToDto(Movie movie) {
        List<String> genreNames = movie.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        return new MovieResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getPosterUrl(),
                movie.getDuration(),
                genreNames
        );
    }
}
