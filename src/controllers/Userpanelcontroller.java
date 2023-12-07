package controllers;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.DBmodels;
import models.Movie;

public class Userpanelcontroller {

    // FXML elements
    @FXML
    private AnchorPane Mybookingpane;

    @FXML
    private AnchorPane barpane;

    @FXML
    private TableColumn<Movie, Date> bookmovie_col_date;

    @FXML
    private TableColumn<Movie, String> bookmovie_col_genre;

    @FXML
    private TableColumn<Movie, String> bookmovie_col_title;

    @FXML
    private TableView<Movie> bookmovie_table;

    @FXML
    private Button bookmoviebutton;

    @FXML
    private ImageView image_pic;

    @FXML
    private AnchorPane movieimagepane;

    @FXML
    private AnchorPane movietablepane;

    @FXML
    private Button mybookingbutton;

    @FXML
    private Label mybookings_date;

    @FXML
    private Label user_name;

    @FXML
    private Label mybookings_total;

    @FXML
    private Label mybookings_ticketid;

    @FXML
    private Label mybookings_title;

    @FXML
    private Button purchase_clearbtn;

    @FXML
    private Label purchaseprise_normal;

    @FXML
    private Label purchaseprise_premium;

    @FXML
    private Label purchaseprise_total;

    @FXML
    private Spinner<Integer> purchasequantity_normal;

    @FXML
    private Spinner<Integer> purchasequantity_premium;

    @FXML
    private AnchorPane purchaseticketspane;

    @FXML
    private Button selectmovie_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private Label selectmovie_date;

    @FXML
    private Label selectmovie_genre;

    @FXML
    private Label selectmovie_title;

    @FXML
    private Label imagetitle_label;

    @FXML
    private AnchorPane selectmoviebuttonpane;

    @FXML
    private Button minimize_btn;

    // Variables
    private int movieId_fromTab;
    private String movie_title_fromtab;
    // Spinner initialization
    public SpinnerValueFactory<Integer> spinner1;
    public SpinnerValueFactory<Integer> spinner2;
    public double price1;
    public double price2;
    public double total;
    public double quantity1;
    public double quantity2;

    // User details
    private String username;

    // Database operations
    DBmodels dbOperations = new DBmodels();

    // Initializes the controller
    @FXML
    void initialize() {
        // Initialize components and data
        showSpinnerValue();
        clearinfo();
        bookmovie();
        minimize_btn.setOnAction(e -> {
            ((Stage) minimize_btn.getScene().getWindow()).setIconified(true);
        });
        
        // Load movie data into the table
        loadMoviesData(bookmovie_table, bookmovie_col_title, bookmovie_col_date, bookmovie_col_genre);
    }

    // Handles the book movie button action
    @FXML
    void bookmovie() {
        Mybookingpane.setVisible(false);
        movieimagepane.setVisible(true);
        movietablepane.setVisible(true);
        selectmoviebuttonpane.setVisible(true);
        purchaseticketspane.setVisible(true);
        showSpinnerValue();
    }

    // Handles the my bookings button action
    @FXML
    void mybookings() {
        Mybookingpane.setVisible(true);
        movieimagepane.setVisible(false);
        movietablepane.setVisible(false);
        selectmoviebuttonpane.setVisible(false);
        purchaseticketspane.setVisible(false);
        receipt();
    }

    // Loads movie data into the TableView
    private void loadMoviesData(TableView<Movie> tableView, TableColumn<Movie, String> movieTitleColumn,
            TableColumn<Movie, java.sql.Date> moviePublishDateColumn, TableColumn<Movie, String> movieGenreColumn) {
        List<Movie> movies = dbOperations.getMovies();
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(movies);
        tableView.setItems(observableMovies);
        movieTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        moviePublishDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPublishDate()));
        movieGenreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
    }

    // Handles the selection of movies in the TableView
    public void select_movies(MouseEvent event) {
        Movie movie = bookmovie_table.getSelectionModel().getSelectedItem();
        int num = bookmovie_table.getSelectionModel().getSelectedIndex();
        movieId_fromTab = movie.getId();
        System.out.println(movieId_fromTab);
        if ((num - 1) < -1) {
            return;
        }
        selectmovie_title.setText(movie.getTitle());
        selectmovie_date.setText(movie.getPublishDate().toString());
        selectmovie_genre.setText(movie.getGenre().toString());
        movie_title_fromtab = movie.getTitle();
    }

    // Handles the select movie button action
    @FXML
    void selectmovie_btn() {
        Alert alert;
        if (selectmovie_title.getText().isEmpty() || selectmovie_date.getText().isEmpty()
                || selectmovie_genre.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the movie");
            alert.showAndWait();
        } else {
            Movie movie = bookmovie_table.getSelectionModel().getSelectedItem();
            imagetitle_label.setText(movie.getTitle());
            String rawimgurl = movie.getImgUrl();
            String imagePath = rawimgurl.replaceFirst("file:", "");
            File imageFile = new File(imagePath);
            Image image = new Image(imageFile.toURI().toString(), 200, 200, false, true);
            image_pic.setImage(image);
            selectmovie_title.setText("");
            selectmovie_date.setText("");
            selectmovie_genre.setText("");
        }
    }

 
    // Shows spinner values
    public void showSpinnerValue() {
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchasequantity_premium.setValueFactory(spinner1);
        purchasequantity_normal.setValueFactory(spinner2);
    }

    // Handles spinner value change event
    public void spinnervalue(MouseEvent event) {
        quantity1 = purchasequantity_premium.getValue();
        quantity2 = purchasequantity_normal.getValue();
        price1 = (quantity1 * 25);
        price2 = (quantity2 * 15);
        total = (price1 + price2);
        purchaseprise_premium.setText("$" + String.valueOf(price1));
        purchaseprise_normal.setText("$" + String.valueOf(price2));
        purchaseprise_total.setText("$" + String.valueOf(total));
    }

    // Handles the buy button action
    @FXML
    void purchase_buybtn() {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp bookedtime = new Timestamp(currentTimeMillis);
        dbOperations.Bookticket(username, movieId_fromTab, movie_title_fromtab, bookedtime, quantity1, quantity2, total);
    }

    // Sets the username
    public void setUsername(String username) {
        this.username = username;
        user_name.setText(username);
    }

    // Clears purchase information
    public void clearinfo() {
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchasequantity_premium.setValueFactory(spinner1);
        purchasequantity_normal.setValueFactory(spinner2);
        purchaseprise_premium.setText("$0.0");
        purchaseprise_normal.setText("$0.0");
        purchaseprise_total.setText("$0.0");
    }

    // Closes the application
    public void close() {
        System.exit(0);
    }

    // Displays the receipt
    public void receipt() {
        models.User_tickets latestBooking = dbOperations.getLatestBookingForUser1(username);
        if (latestBooking != null) {
            mybookings_ticketid.setText(" " + latestBooking.getTicketId());
            mybookings_title.setText(latestBooking.getMovieTitle());
            mybookings_total.setText("$" + latestBooking.getTotal());
            mybookings_date.setText(" " + latestBooking.getDate());
        } else {
            mybookings_ticketid.setText("No booking found for the user or an error occurred.");
        }
    }

    // Handles the sign out button action
    @FXML
    void signout_user() {
        try {
            Window window = signout_btn.getScene().getWindow();
            if (window != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to sign out?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    System.out.println("Signing out...");
                    window.hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginPanel.fxml"));
                    Parent root1 = loader.load();
                    Stage stage = (Stage) signout_btn.getScene().getWindow();
                    Scene scene = new Scene(root1);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                System.out.println("Window is null. Button not attached to a scene?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
