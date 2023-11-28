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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.DBmodels;
import models.DBmodels.Movie;
public class Userpanelcontroller  {    

	private static final java.sql.Timestamp Timestamp = null;

	@FXML
	private AnchorPane Mybookingpane;

	@FXML
	private AnchorPane barpane;

	@FXML
	private TableColumn<DBmodels.Movie, Date> bookmovie_col_date;

	@FXML
	private TableColumn<DBmodels.Movie, String> bookmovie_col_genre;

	@FXML
	private TableColumn<DBmodels.Movie, String> bookmovie_col_title;

	@FXML
	private TableView<DBmodels.Movie> bookmovie_table;

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
	private Label mybookings_genre;
	
	@FXML
	private Label user_name;
	
	

	@FXML
	private ImageView mybookings_img;

	@FXML
	private Label mybookings_ticketid;

	@FXML
	private Label mybookings_time;

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
	private Label selectmovie_date;

	@FXML
	private Label selectmovie_genre;

	@FXML
	private Label selectmovie_title;

	@FXML
	private Label imagetitle_label;

	@FXML
	private AnchorPane selectmoviebuttonpane;
	
	private int movieId_fromTab;
	
	private String movie_title_fromtab;

	DBmodels dbOperations = new DBmodels();

	@FXML
	void bookmovie() {
		Mybookingpane.setVisible(false);
		movieimagepane.setVisible(true);
		movietablepane.setVisible(true);
		selectmoviebuttonpane.setVisible(true);
		purchaseticketspane.setVisible(true);

		showSpinnerValue();

	}

	@FXML
	void mybookings() {
		Mybookingpane.setVisible(true);
		movieimagepane.setVisible(false);
		movietablepane.setVisible(false);
		selectmoviebuttonpane.setVisible(false);
		purchaseticketspane.setVisible(false);


	}

	private void loadMoviesData(TableView<Movie> tableView, TableColumn<Movie, String> movieTitleColumn, TableColumn<Movie, java.sql.Date> moviePublishDateColumn, TableColumn<Movie, String> movieGenreColumn)  
	{
		List<Movie> movies = dbOperations.getMovies();

		// Load data into the TableView
		ObservableList<Movie> observableMovies = FXCollections.observableArrayList(movies);
		tableView.setItems(observableMovies);

		movieTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		moviePublishDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPublishDate()));
		movieGenreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));

	}

	@FXML
	void initialize() {

		showSpinnerValue();


//		spinnervalue();
		
		
		loadMoviesData(bookmovie_table, bookmovie_col_title, bookmovie_col_date, bookmovie_col_genre);

	}

	public void select_movies(MouseEvent event) {
		Movie movie = bookmovie_table.getSelectionModel().getSelectedItem();
		int num = bookmovie_table.getSelectionModel().getSelectedIndex();
		movieId_fromTab =movie.getId();
		System.out.println(movieId_fromTab);

		if((num - 1) < -1) {
			return;
		}
		selectmovie_title.setText(movie.getTitle());
		selectmovie_date.setText(movie.getPublishDate().toString());
		selectmovie_genre.setText(movie.getGenre().toString());
		movie_title_fromtab= movie.getTitle();

	}

	@FXML
	void selectmovie_btn() {

		Alert alert;
		if( selectmovie_title.getText().isEmpty() ||selectmovie_date.getText() .isEmpty() || selectmovie_genre.getText().isEmpty() ) {
			alert = new Alert (AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please select the movie");
			alert.showAndWait();

		}
		else {
			Movie movie = bookmovie_table.getSelectionModel().getSelectedItem();
			imagetitle_label.setText(movie.getTitle());

			String rawimgurl=movie.getImgUrl();	   
			String imagePath = rawimgurl.replaceFirst("file:", "");
			File imageFile = new File(imagePath);
			Image image = new Image(imageFile.toURI().toString(),200,200,false,true);
			image_pic.setImage(image);

			selectmovie_title.setText("");
			selectmovie_date.setText("");
			selectmovie_genre.setText("");

		} 

	}

	public SpinnerValueFactory<Integer> spinner1;
	public SpinnerValueFactory<Integer> spinner2;
	public double price1;
	public double price2;
	public double total;
	public double q1;
	public double q2;

	private String username;

	public void showSpinnerValue() {
		spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
		spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

		purchasequantity_premium.setValueFactory(spinner1);
		purchasequantity_normal.setValueFactory(spinner2);

	}
	
	public void spinnervalue(MouseEvent event) {
		q1 = purchasequantity_premium.getValue();
		q2 = purchasequantity_normal.getValue();
		price1 = (q1*25);
		price2 = (q2*15);
		total = (price1 + price2);
		purchaseprise_premium.setText("$"+ String.valueOf(price1));
		purchaseprise_normal.setText("$"+ String.valueOf(price2));
		purchaseprise_total.setText("$"+ String.valueOf(total));
		
		
	}
	
	
	

	
	   @FXML
	    void purchase_buybtn() {
		   
		   long currentTimeMillis = System.currentTimeMillis();
		   Timestamp bookedtime = new Timestamp(currentTimeMillis);
		   
		   dbOperations.Bookticket(username, movieId_fromTab, movie_title_fromtab, bookedtime, q1,q2,total);

	    }
	   
	   public void setUsername(String username) {
	        this.username = username;
	        user_name.setText(username); 
	       
	    }
	   

      


}









