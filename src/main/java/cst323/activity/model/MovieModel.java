package cst323.activity.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Component
@Entity
@Table(name = "movies")
public class MovieModel {
    // ---- ATTRIBUTES ----

    @Id // Unique Identifier
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Movie title is required.")
    @Size(min = 1, max = 125, message = "Title limit is 125 characters")
    private String title;

    @Column(name = "release_year")
    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2099, message = "Year must be before 2100")
    private Integer year;

    @Column(name = "genre")
    @Size(max = 45, message = "Genre limit is 45 characters")
    private String genre;

    @Column(name = "rating")
    @Min(value = 0, message = "Rating must be from 0-10")
    @Max(value = 10, message = "Rating must be from 0-10")
    private Integer rating;

    @Column(name = "image")
    private String image;

    // ---- CONSTRUCTORS ----

    public MovieModel() {
        id = 0L;
        title = "";
        year = 0;
        genre = "";
        rating = 0;
        image = "";
    }

    public MovieModel(Long id,
            @NotNull(message = "Movie title is required.") @Size(min = 1, max = 125, message = "Title limit is 125 characters") String title,
            @Size(max = 4, message = "Year is a 4 digit format") int year,
            @Size(max = 45, message = "Genre limit is 45 characters") String genre,
            @Size(min = 0, max = 10, message = "Rating is from 0 to 10") int rating, String image) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.image = image;
    }

    // ---- ACCESSORS AND MUTATORS ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        System.out.println("getting title");

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
