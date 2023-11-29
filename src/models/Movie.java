package models;

import java.sql.Timestamp;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private java.sql.Date publishDate;
    private java.sql.Time duration;
    private String imgUrl;

    public Movie(int id, String title, String genre, java.sql.Date publishDate, java.sql.Time duration, String imgUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.publishDate = publishDate;
        this.duration = duration;
        this.imgUrl = imgUrl;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public java.sql.Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(java.sql.Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public java.sql.Time getDuration() {
		return duration;
	}

	public void setDuration(java.sql.Time duration) {
		this.duration = duration;
	}
    
}

