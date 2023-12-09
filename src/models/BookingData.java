package models;

import java.sql.Timestamp;

public class BookingData {
	private String bookingId;
	private String username;
	private String movieId;
	private String movieTitle;
	private Timestamp timestamp;
	private double no_of_premium_tickets;
	private double no_of_normal_tickets;
	private double total_cost;
	private String u_name;




	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public double getNo_of_premium_tickets() {
		return no_of_premium_tickets;
	}
	public void setNo_of_premium_tickets(double no_of_premium_tickets) {
		this.no_of_premium_tickets = no_of_premium_tickets;
	}
	public double getNo_of_normal_tickets() {
		return no_of_normal_tickets;
	}
	public void setNo_of_normal_tickets(double no_of_normal_tickets) {
		this.no_of_normal_tickets = no_of_normal_tickets;
	}
	public double getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

}
