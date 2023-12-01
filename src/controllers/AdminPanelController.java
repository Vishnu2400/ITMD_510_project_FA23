package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.BookingData;
import models.DBmodels;
import models.Movie;

public class AdminPanelController {

	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	private Button cleartxtfieldsbtn;

	@FXML
	private Button delmoviebtn;

	@FXML
	private Button importimgurlbtn;

	@FXML
	private Button insertmoviebtn;

	private String imageUrl;

	@FXML
	private Button signoutid;


	@FXML
	private AnchorPane addmovieimg;

	@FXML
	private ImageView movieimageview;


	@FXML
	private TableView<BookingData> customertabview;

	@FXML
	private TableColumn<BookingData, String> ticketId_col;

	@FXML
	private TableColumn<BookingData, String> customerID_col;

	@FXML
	private TableColumn<BookingData, String> customerName_col;

	@FXML
	private TableColumn<BookingData, String> movieId_col;

	@FXML
	private TableColumn<BookingData, String> movieName_col;

	@FXML
	private TableColumn<BookingData, Timestamp> timestamp_col;

	@FXML
	private TableColumn<BookingData, Double> no_of_premium_tik;

	@FXML
	private TableColumn<BookingData, Double> no_of_normal_tik;

	@FXML
	private TableColumn<BookingData, Double> total_amt_col;


	@FXML
	private TextField txtdurationin;

	@FXML
	private Button minimizebtn;

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
	private TableView<Movie> moviesTableView1;

	@FXML
	private TableColumn<Movie, Integer> movieIdColumn1;

	@FXML
	private TableColumn<Movie, String> movieTitleColumn1;

	@FXML
	private TableColumn<Movie, String> movieGenreColumn1;

	@FXML
	private TableColumn<Movie, Date> moviePublishDateColumn1;

	@FXML
	private TableColumn<Movie, String> Moviedurationcoloumn1;

	@FXML
	private TableView<Movie> moviesTableView11;

	@FXML
	private TableColumn<Movie, Integer> movieIdColumn11;

	@FXML
	private TableColumn<Movie, String> movieTitleColumn11;

	@FXML
	private TableColumn<Movie, String> movieGenreColumn11;

	@FXML
	private TableColumn<Movie, Date> moviePublishDateColumn11;

	@FXML
	private TableColumn<Movie, String> Moviedurationcoloumn11;

	@FXML
	private AnchorPane adminmainpane;




	@FXML
	private VBox vboxmenu;

	private final DBmodels dbOperations = new DBmodels();

	public void initialize() {

		handleshowmoviesplaying();

		minimizebtn.setOnAction(e -> {
			((Stage) minimizebtn.getScene().getWindow()).setIconified(true);
		});

		adminmainpane.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		adminmainpane.setOnMouseDragged(event -> {
			Stage stage = (Stage) adminmainpane.getScene().getWindow();
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
		});

	}


	private void loadMoviesData(TableView<Movie> tableView, TableColumn<Movie, Integer> movieIdColumn,
			TableColumn<Movie, String> movieTitleColumn, TableColumn<Movie, java.sql.Date> moviePublishDateColumn,
			TableColumn<Movie, String> Moviedurationcoloumn, TableColumn<Movie, String> movieGenreColumn) {
		List<Movie> movies = dbOperations.getMovies();

		// Load data into the TableView
		ObservableList<Movie> observableMovies = FXCollections.observableArrayList(movies);
		tableView.setItems(observableMovies);

		// Map the columns to the properties of the Movie class
		movieIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		movieTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		moviePublishDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPublishDate()));
		Moviedurationcoloumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration().toString()));
		movieGenreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
	}



	@FXML
	private void handleAddMoviesButton() {
		selectMoviepane.setVisible(true);
		movieTablepane.setVisible(true);
		moviesPlayingpane.setVisible(false);
		updateMoviePane.setVisible(false);

		loadMoviesData(moviesTableView11, movieIdColumn11, movieTitleColumn11, moviePublishDateColumn11, Moviedurationcoloumn11, movieGenreColumn11);


	}


	public void selectaddremMovie(MouseEvent event) {
		Movie movieData = moviesTableView11.getSelectionModel().getSelectedItem();
		int selNum = moviesTableView11.getSelectionModel().getSelectedIndex();

		if((selNum-1)<-1) {
			return;
		}

		txtmovietitlein.setText(movieData.getTitle());
		txtmoviegenrein.setText(movieData.getGenre());
		txtpublishdate.setValue(movieData.getPublishDate().toLocalDate());
		txtdurationin.setText(movieData.getDuration().toString());

		String movieimg = movieData.getImgUrl();

		try {
			URI uri = URI.create(movieimg);
			Path path = Paths.get(uri);
			Image image = new Image(path.toUri().toString(),184,186,false,true);
			movieimageview.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	//	public void importImage() {
	//		FileChooser open = new FileChooser();
	//		open.setTitle("Open Image File");
	//		open.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg","*.JPEG"));
	//
	//		Stage stage = (Stage) selectMoviepane.getScene().getWindow();
	//
	//		File file = open.showOpenDialog(stage);
	//
	//		if (file != null) {
	//			imageUrl = file.toURI().toString();
	//			double desiredWidth = 184;
	//			double desiredHeight = 186;
	//			Image image = new Image(imageUrl, desiredWidth, desiredHeight, false, true);
	//			movieimageview.setImage(image);
	//		}
	//	}

	public void importImage() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image File");

		// Set the initial directory to the "images" folder
		File initialDirectory = new File("@../../images");
		fileChooser.setInitialDirectory(initialDirectory);

		// Add filters to allow only image files
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
				);

		Stage stage = (Stage) selectMoviepane.getScene().getWindow();

		// Show the file chooser dialog
		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			// Get the path to the selected image file
			imageUrl = selectedFile.toURI().toString();

			// Load and display the image
			double desiredWidth = 184;
			double desiredHeight = 186;
			Image image = new Image(imageUrl, desiredWidth, desiredHeight, false, true);
			movieimageview.setImage(image);
		}
	}


	private String getImageUrlFromImageView() {
		// Assuming you have a method to get the image URL from ImageView
		Image image = movieimageview.getImage();

		if (image != null) {
			return image.getUrl();
		} else {
			// Handle the case where no image is set
			return null;
		}
	}



	@FXML
	void addmoviebtn() {

		if (imageUrl != null) {
			dbOperations.addMovie(
					txtmovietitlein.getText(),
					txtmoviegenrein.getText(),
					txtpublishdate.getValue().toString(),
					txtdurationin.getText(),
					getImageUrlFromImageView()
					);
		} else {
			// Handle the case where no image is selected
			System.out.println("Please select an image.");
		}
	}

	@FXML
	void updmoviebtn() {
		Movie movieData = moviesTableView11.getSelectionModel().getSelectedItem();

		int movieId = movieData.getId();
		String updatedTitle = txtmovietitlein.getText();
		String updatedGenre = txtmoviegenrein.getText();
		String updatedPublishDate = txtpublishdate.getValue().toString();
		String updatedDuration = txtdurationin.getText();
		String updatedImgUrl = getImageUrlFromImageView();

		// Update the movie in the database
		dbOperations.updateMovie(movieId, updatedTitle, updatedGenre, updatedPublishDate, updatedDuration, updatedImgUrl);


	}

	@FXML
	void cleartxtfieldsbtn() {

		txtmovietitlein.clear();
		txtmoviegenrein.clear();
		txtpublishdate.setValue(null);
		txtdurationin.clear();
		movieimageview.setImage(null);



	}


	private void loadBookingData(TableView<BookingData> tableView, TableColumn<BookingData, String> usernameColumn,TableColumn<BookingData, String> uname,
			TableColumn<BookingData, String> movieIdColumn,TableColumn<BookingData, String> movieTitleColumn, TableColumn<BookingData, String> bookingIdColumn,
			TableColumn<BookingData, Timestamp> timestampColumn,TableColumn<BookingData, Double> premiumTicketsColumn, TableColumn<BookingData, Double> normalTicketsColumn,
			TableColumn<BookingData, Double> totalColumn) 
	{
		List<BookingData> userDataList = dbOperations.fetchUserBookingdata();

		// Load data into the TableView
		ObservableList<BookingData> observableUserData = FXCollections.observableArrayList(userDataList);
		tableView.setItems(observableUserData);

		// Map the columns to the properties of the BookingData class
		bookingIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingId()));
		usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
		movieIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieId()));
		movieTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieTitle()));
		timestampColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Timestamp>(cellData.getValue().getTimestamp()));
		premiumTicketsColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNo_of_premium_tickets()).asObject());
		normalTicketsColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNo_of_normal_tickets()).asObject());
		totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal_cost()).asObject());
		uname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getU_name()));
	}

	@FXML
	void deletemoviebtn() {

		Movie movieData = moviesTableView11.getSelectionModel().getSelectedItem();

		int movieId = movieData.getId();

		// Update the movie in the database
		dbOperations.deleteMovie(movieId);

	}

	@FXML
	private void handlecustomerbtn() {

		selectMoviepane.setVisible(false);
		movieTablepane.setVisible(false);
		moviesPlayingpane.setVisible(false);
		updateMoviePane.setVisible(true);

		loadBookingData(customertabview,
				customerID_col,
				customerName_col,
				movieId_col,
				movieName_col,
				ticketId_col,
				timestamp_col,
				no_of_premium_tik,
				no_of_normal_tik,
				total_amt_col);

	}



	@FXML
	private void handleshowmoviesplaying() {

		selectMoviepane.setVisible(false);
		movieTablepane.setVisible(false);
		moviesPlayingpane.setVisible(true);
		updateMoviePane.setVisible(false);

		loadMoviesData(moviesTableView1, movieIdColumn1, movieTitleColumn1, moviePublishDateColumn1, Moviedurationcoloumn1, movieGenreColumn1);


	}

	@FXML
	void refreshtable() {

		loadMoviesData(moviesTableView11, movieIdColumn11, movieTitleColumn11, moviePublishDateColumn11, Moviedurationcoloumn11, movieGenreColumn11);
	}

	@FXML
	void customer_tab_refresh() {

		loadBookingData(customertabview,
				customerID_col,
				customerName_col,
				movieId_col,
				movieName_col,
				ticketId_col,
				timestamp_col,
				no_of_premium_tik,
				no_of_normal_tik,
				total_amt_col);

	}

	@FXML
	void signout() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginPanel.fxml"));
			Parent root = loader.load();

			// Get the stage from the current scene
			Stage stage = (Stage) signoutid.getScene().getWindow();

			// Create a new scene with the login panel
			Scene scene = new Scene(root);

			// Set the stage's scene to the new scene
			stage.setScene(scene);

			// Show the stage
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	@FXML
	void exit() {
		System.exit(0);
	}



}
