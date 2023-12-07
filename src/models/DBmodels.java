// DatabaseOperations.java
package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DBmodels {

	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	public boolean loginUser(String username, String password, String userType) {
        conn = mysqlconnect.connectdb();
        String sql = "SELECT * FROM moviebooking_user_table WHERE U_username = ? AND U_type = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, userType);

            rs = pst.executeQuery();

            // If a matching user is found
            if (rs.next()) {
                // Retrieve the stored hash from the database
                String storedHash = rs.getString("U_password");

                // Hash the entered password for comparison
                String enteredPasswordHash = hashPassword(password);

                // Compare the stored hash with the hash of the entered password
                return enteredPasswordHash != null && enteredPasswordHash.equals(storedHash);
            }

            return false; // No matching user found
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        } finally {
            closeResources();
        }
    }

	public ObservableList<Users> fetchUserdata() {

		ObservableList<Users> userinfo = FXCollections.observableArrayList();
		conn = mysqlconnect.connectdb();
		String sql = " SELECT * FROM moviebooking_user_table ";
		try {
			pst=conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				Users userInfo = new Users();

				userInfo.setUsername(rs.getString("U_username"));
				userInfo.setEmail_id(rs.getString("U_email"));
				userInfo.setPassword(rs.getString("U_password"));
				userInfo.setName(rs.getString("U_name"));
				userInfo.setType_of_user(rs.getString("U_type"));

				userinfo.add(userInfo);

			}

		}catch (Exception e) {

			e.printStackTrace();

		}finally {

			closeResources();

		}

		return userinfo;

	}




	public User_tickets getLatestBookingForUser1(String username) {
		try {
			conn = mysqlconnect.connectdb();
			String query = "SELECT * FROM moviebooking_booking_table WHERE U_username = ? ORDER BY booking_id DESC LIMIT 1";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int retrievedTicketId = resultSet.getInt("booking_id");
				double retrievedTotal = resultSet.getDouble("total");
				Timestamp retrievedDate = resultSet.getTimestamp("booking_timestamp");
				String retrievedMovieTitle = resultSet.getString("Movie_title");

				return new User_tickets(retrievedTicketId, retrievedTotal, retrievedDate, retrievedMovieTitle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // Return null if no data found or an error occurred
	}


	public ObservableList<BookingData> fetchUserBookingdata() {
		ObservableList<BookingData> userData = FXCollections.observableArrayList();
		conn = mysqlconnect.connectdb();
		String sql = "SELECT b.booking_id, b.U_username, b.Movie_id, b.Movie_title, " +
				"b.booking_timestamp, b.booking_no_premium_tickets, " +
				"b.booking_no_normal_tickets, b.total, u.U_name "+
				"FROM moviebooking_booking_table b " +
				"JOIN moviebooking_user_table u ON b.U_username = u.U_username ";

		try {
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				BookingData bookingData = new BookingData();

				bookingData.setUsername(rs.getString("U_username"));
				bookingData.setU_name(rs.getString("U_name"));
				bookingData.setMovieId(rs.getString("Movie_id"));
				bookingData.setMovieTitle(rs.getString("Movie_title"));
				bookingData.setBookingId(rs.getString("booking_id"));
				bookingData.setTimestamp(rs.getTimestamp("booking_timestamp"));
				bookingData.setNo_of_premium_tickets(rs.getDouble("booking_no_premium_tickets"));
				bookingData.setNo_of_normal_tickets(rs.getDouble("booking_no_normal_tickets"));
				bookingData.setTotal_cost(rs.getDouble("total"));


				userData.add(bookingData);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);

		} finally {
			closeResources();
		}
		return userData;
	}


	public void addUser(String username, String email, String password, String name, String userType) {
        conn = mysqlconnect.connectdb();
        String sql = "INSERT INTO moviebooking_user_table (U_username, U_email, U_password, U_name, U_type) VALUES (?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, email);

            // Hash the password before storing it in the database
            String hashedPassword = hashPassword(password);
            pst.setString(3, hashedPassword);

            pst.setString(4, name);
            pst.setString(5, userType);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Added user!");
        } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
            JOptionPane.showMessageDialog(null, "Duplicate entry for primary key. User with the same username already exists.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeResources();
        }
    }


	public void UpdateUser(String username, String email, String password, String name, String userType) {
        conn = mysqlconnect.connectdb();
        String sql = "UPDATE moviebooking_user_table SET U_email = ?, U_password = ?, U_name = ?, U_type = ? WHERE U_username = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);

            // Hash the password before updating it in the database
            String hashedPassword = hashPassword(password);
            pst.setString(2, hashedPassword);

            pst.setString(3, name);
            pst.setString(4, userType);
            pst.setString(5, username);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated user!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeResources();
        }
    }

	public String countMovies() {
		conn = mysqlconnect.connectdb();
		String sql = "SELECT COUNT(Movie_id) AS movie_count FROM moviebooking_movie_table";

		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getString("movie_count");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			closeResources();
		}

		// Return -1 or another appropriate value in case of an error
		return null;
	}

	public Map<String, Double> getBookingSummary() {
		conn = mysqlconnect.connectdb();
		String sql = "SELECT SUM(booking_no_premium_tickets) AS no_Of_Premium_tickets, " +
				"SUM(booking_no_normal_tickets) AS no_Of_Normal_tickets, " +
				"SUM(total) AS total_sum FROM moviebooking_booking_table";

		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			if (rs.next()) {
				Map<String, Double> summaryMap = new HashMap<>();
				summaryMap.put("no_Of_Premium_tickets", rs.getDouble("no_Of_Premium_tickets"));
				summaryMap.put("no_Of_Normal_tickets", rs.getDouble("no_Of_Normal_tickets"));
				summaryMap.put("total_sum", rs.getDouble("total_sum"));
				return summaryMap;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			closeResources();
		}

		// Return null or another appropriate value in case of an error
		return null;
	}





	public void addMovie(String title, String genre, String publishDate, String duration, String imgUrl) {
		conn = mysqlconnect.connectdb();
		String sql = "INSERT INTO moviebooking_movie_table (Movie_title, Movie_genre, Movie_publishdate, Movie_duration, Movie_imgurl) VALUES (?, ?, ?, ?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, title);
			pst.setString(2, genre);
			pst.setString(3, publishDate);
			pst.setString(4, duration);
			pst.setString(5, imgUrl);
			pst.execute();

			JOptionPane.showMessageDialog(null, "Added movie!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			closeResources();
		}
	}

	// Inside the DBmodels class

	public void updateMovie(int movieId, String title, String genre, String publishDate, String duration, String imgUrl) {
		conn = mysqlconnect.connectdb();
		String sql = "UPDATE moviebooking_movie_table SET Movie_title = ?, Movie_genre = ?, Movie_publishdate = ?, Movie_duration = ?, Movie_imgurl = ? WHERE Movie_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, title);
			pst.setString(2, genre);
			pst.setString(3, publishDate);
			pst.setString(4, duration);
			pst.setString(5, imgUrl);
			pst.setInt(6, movieId);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Updated movie!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			closeResources();
		}
	}


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
						rs.getTime("Movie_duration"),
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

	public void deleteuser(String User,String user_type) {
		conn = mysqlconnect.connectdb();
		String sql = "DELETE FROM moviebooking_user_table WHERE U_username = ? AND U_type = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, User);
			pst.setString(2, user_type);
			pst.execute();

			JOptionPane.showMessageDialog(null, "Deleted User!");
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
	
	private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());

            // Convert byte array to a hexadecimal representation
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return null;
        }
    }

	public void updatePassword(String username, String newPassword) {
        String updateQuery = "UPDATE moviebooking_user_table SET U_password = ? WHERE U_username = ?";
        conn = mysqlconnect.connectdb();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            // Hash the new password before updating it in the database
            String hashedPassword = hashPassword(newPassword);
            preparedStatement.setString(1, hashedPassword);

            preparedStatement.setString(2, username);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
            	JOptionPane.showMessageDialog(null, "Password updated successfully !");
            } else {
            	JOptionPane.showMessageDialog(null, "No user found with the" +username+" !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        } finally {
            closeResources();
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

}
