package controllers;

import java.sql.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.DBmodels;
import models.DBmodels.Movie;

public class AdminPanelController {

    @FXML
    private TableView<?> addremovetabview;

    @FXML
    private TableColumn<?, ?> adduration;

    @FXML
    private TableColumn<?, ?> admovieid;

    @FXML
    private TableColumn<?, ?> admovietitle;

    @FXML
    private TableColumn<?, ?> adnoviegenre;

    @FXML
    private TableColumn<?, ?> adpublishdate;

    @FXML
    private Button cleartxtfieldsbtn;

    @FXML
    private Button delmoviebtn;

    @FXML
    private Button importimgurlbtn;

    @FXML
    private Button insertmoviebtn;


    @FXML
    private AnchorPane addmovieimg;


    @FXML
    private Button searchbtn;

    @FXML
    private TextField searchtxtbox;

  
    @FXML
    private TextField txtdurationin;

    @FXML
    private TextField txtmoviegenrein;

    @FXML
    private TextField txtmovietitlein;

    @FXML
    private DatePicker txtpublishdate;

    @FXML
    private Button updatemoviebtn;

	@FXML
    private AnchorPane bannerPane;

    @FXML
    private AnchorPane movieTablepane;

    @FXML
    private AnchorPane moviesPlayingpane;

    @FXML
    private AnchorPane selectMoviepane;

    @FXML
    private AnchorPane updateMoviePane;
    
    @FXML
    private TableView<DBmodels.Movie> moviesTableView;

    @FXML
    private TableColumn<DBmodels.Movie, Integer> movieIdColumn;

    @FXML
    private TableColumn<DBmodels.Movie, String> movieTitleColumn;

    @FXML
    private TableColumn<DBmodels.Movie, String> movieGenreColumn;

    @FXML
    private TableColumn<DBmodels.Movie, Date> moviePublishDateColumn;
    
    @FXML
    private TableColumn<DBmodels.Movie, String> Moviedurationcoloumn;


    @FXML
    private VBox vboxmenu;
    
    private final DBmodels dbOperations = new DBmodels();
//    private ObservableList<DBmodels.Movie> moviesData;

	public void initialize() {
		
		handleshowmoviesplaying();
		
	}
	
//	private void loadMoviesData() {
//        // Get movies from the database
//        List<DBmodels.Movie> moviesList = dbOperations.getMovies();
//
//        // Load data into the TableView
//        moviesData = FXCollections.observableArrayList(moviesList);
//        moviesTableView.setItems(moviesData);
//    }



	@FXML
	private void handleAddMoviesButton() {
		selectMoviepane.setVisible(true);
		movieTablepane.setVisible(true);
		moviesPlayingpane.setVisible(false);
		updateMoviePane.setVisible(false);
		// Hide other panes as needed
	}

	@FXML
	private void handleUpdatemovie() {

		selectMoviepane.setVisible(false);
		movieTablepane.setVisible(false);
		moviesPlayingpane.setVisible(false);
		updateMoviePane.setVisible(true);
		// Hide other panes as needed
	}



	@FXML
	private void handleshowmoviesplaying() {

		selectMoviepane.setVisible(false);
		movieTablepane.setVisible(false);
		moviesPlayingpane.setVisible(true);
		updateMoviePane.setVisible(false);
		
		List<Movie> movies = dbOperations.getMovies();

        // Populate the TableView with the data
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(movies);
        moviesTableView.setItems(observableMovies);

        // Map the columns to the properties of the Movie class
        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        movieTitleColumn.setCellValueFactory(new PropertyValueFactory<>("MovieTitle"));
        moviePublishDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        Moviedurationcoloumn.setCellValueFactory(new PropertyValueFactory<>("MovieDuration"));
        movieGenreColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));

	}



}
