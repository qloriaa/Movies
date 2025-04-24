package cst323.activity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cst323.activity.model.MovieModel;

public interface MovieRepository extends JpaRepository<MovieModel, Long> {

    // used to select a Movie
    Optional<MovieModel> findById(Long id);

    // used for search function
    List<MovieModel> findByTitle(String title);

}
