package models;

import java.sql.Timestamp;

public class User_tickets {
    private int ticketId;
    private double total;
    private Timestamp date;
    private String movieTitle;

    public User_tickets(int ticketId, double total, Timestamp date, String movieTitle) {
        this.ticketId = ticketId;
        this.total = total;
        this.date = date;
        this.movieTitle = movieTitle;
    }

    // Getters for the booking details
    public int getTicketId() {
        return ticketId;
    }

    public double getTotal() {
        return total;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
