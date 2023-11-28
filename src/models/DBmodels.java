// DatabaseOperations.java
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import controllers.Userpanelcontroller;

import javax.swing.JOptionPane;


public class DBmodels {

    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public boolean loginUser(String username, String password, String userType) {
        conn = mysqlconnect.connectdb();
        String sql = "SELECT * FROM moviebooking_user_table WHERE U_username = ? AND U_password = ? AND U_type = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, userType);

            rs = pst.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        } finally {
            closeResources();
        }
    }

    public void addUser(String username, String email, String password, String name, String userType) {
        conn = mysqlconnect.connectdb();
        String sql = "INSERT INTO moviebooking_user_table (U_username, U_email, U_password, U_name, U_type) VALUES (?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, name);
            pst.setString(5, userType);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Added user!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeResources();
        }
    }
    
    public void addMovie(String title, String genre, String publishDate, String imgUrl) {
        conn = mysqlconnect.connectdb();
        String sql = "INSERT INTO moviebooking_movie_table (Movie_title, Movie_genre, Movie_publishdate, Movie_imgurl) VALUES (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, genre);
            pst.setString(3, publishDate);
            pst.setString(4, imgUrl);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Added movie!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeResources();
        }
    }
    
//    public ArrayList<String> fetchuser(String username) {
//        ArrayList<String> userdata = new ArrayList<String>();
//            conn = mysqlconnect.connectdb();
//            String sql = "SELECT * FROM moviebooking_user_table WHERE U_username = ? ";
//            try {
//                pst = conn.prepareStatement(sql);
//                pst.setString(1, username);
//
//                rs = pst.executeQuery();
//                userdata.add(rs.getString("U_name"));
//                userdata.add(rs.getString("U_email"));
//     
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, e);
//               
//            } finally {
//                closeResources();
//            }
//            return userdata;
//        }
    
    public List<Movie> getMovies() {
        conn = mysqlconnect.connectdb();
        String sql = "SELECT * FROM moviebooking_movie_table";
        List<Movie> movies = new ArrayList<Movie>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("Movie_id"),
                        rs.getString("Movie_title"),
                        rs.getString("Movie_genre"),
                        rs.getDate("Movie_publishdate"),
                        rs.getString("Movie_imgurl")
                );
                movies.add(movie);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeResources();
        }
        return movies;
    }
    
    public void deleteMovie(int movieId) {
        conn = mysqlconnect.connectdb();
        String sql = "DELETE FROM moviebooking_movie_table WHERE Movie_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, movieId);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Deleted movie!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeResources();
        }
    }
    
    public void Bookticket(String username, int movie_id, String movie_title, Timestamp bookedtime, double no_premium_tickets, double no_of_normal_tickets, double total_cost) {
        String sql = "INSERT INTO moviebooking_booking_table(U_username, Movie_id, Movie_title, booking_timestamp, booking_no_premium_tickets, booking_no_normal_tickets, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conn = mysqlconnect.connectdb();

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);  // Set the username
            pst.setInt(2, movie_id);  // Set the movie_id
            pst.setString(3, movie_title);  // Set the movie_title
            pst.setTimestamp(4, bookedtime);  // Set the bookedtime
            pst.setDouble(5, no_premium_tickets);  // Set the no_premium_tickets
            pst.setDouble(6, no_of_normal_tickets);  // Set the no_of_normal_tickets
            pst.setDouble(7, total_cost);  // Set the total_cost

            pst.execute();

            JOptionPane.showMessageDialog(null, "Booked movie!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }


    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public class Movie {
        private int id;
        private String title;
        private String genre;
        private java.sql.Date publishDate;
        private String imgUrl;

        public Movie(int id, String title, String genre, java.sql.Date publishDate, String imgUrl) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.publishDate = publishDate;
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
        
    }
    
}
