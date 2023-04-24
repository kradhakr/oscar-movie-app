package com.oscar.movie.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id",
		scope = Movie.class)
public class Movie extends AuditModel{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "genre")
	private String genre;

	@Column(name = "release_year")
	private int releaseYear;

	@Column(name = "rating_description")
	private String ratingDescription;

	@Column(name = "total_rating")
	private double totalRating;

	@Column(name = "box_office_value")
	private long boxOfficeValue;

	public Movie(String title, String genre, int releaseYear, String ratingDescription,
				 double totalRating, long boxOfficeValue) {
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseYear;
		this.ratingDescription = ratingDescription;
		this.totalRating = totalRating;
		this.boxOfficeValue = boxOfficeValue;
	}
}
