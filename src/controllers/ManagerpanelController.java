package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DBmodels;
import models.Users;

public class ManagerpanelController {



	@FXML
	private TableColumn<Users, String> Admin_name_col;

	@FXML
	private AnchorPane addAdminpane;

	@FXML
	private TextField admin_email;

	@FXML
	private TableColumn<Users, String> admin_email_col;

	@FXML
	private TextField admin_name;

	@FXML
	private TextField admin_password;

	@FXML
	private TableColumn<Users, String> admin_password_col;

	@FXML
	private TableView<Users> admin_update_table;

	@FXML
	private TextField admin_username;

	@FXML
	private TableColumn<Users, String> admin_username_col;

	@FXML
	private AnchorPane cashdashboardpane;

	@FXML
	private Button minimizeid;

	@FXML
	private ImageView img1;

	@FXML
	private ImageView img2;

	@FXML
	private ImageView img3;

	@FXML
	private AnchorPane managermainpane;

	@FXML
	private Button signoutid;

	@FXML
	private AnchorPane scrollImgpane;

	@FXML
	private TextField type_field;

	@FXML
	private Label no_of_moviesvalue;

	@FXML
	private Label normal_ticket_value;

	@FXML
	private Label pre_ticket_value;

	@FXML
	private Label total_earnings_value;

	@FXML
	private VBox vboxmenu;

	private double xOffset = 0;
	private double yOffset = 0;

	private int currentVisibleIndex = 0;

	public void initialize() {

		minimizeid.setOnAction(e -> {
			((Stage) minimizeid.getScene().getWindow()).setIconified(true);
		});

		managermainpane.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		managermainpane.setOnMouseDragged(event -> {
			Stage stage = (Stage) managermainpane.getScene().getWindow();
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
		});

		handleDashboardButton();

		setImages();

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(5), event -> {
					currentVisibleIndex = (currentVisibleIndex + 1) % 3;
					setImages();
				})
				);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();


	}



	@FXML
	void addadimbtn() {
		// Get text from text fields
		String username = admin_username.getText();
		String email = admin_email.getText();
		String password = admin_password.getText();
		String name = admin_name.getText();

		// Check if any of the fields is empty
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty()) {
			System.err.println("Please fill in all the fields before adding a user.");
		} else {
			try {
				// Call the addUser method if all fields are filled
				dbOperations.addUser(username, email, password, name, "Admin");
			} catch (Exception e) {
				System.err.println("An error occurred while adding the user: " + e.getMessage());
				// Handle the exception as needed
			}
		}
	}

	private final DBmodels dbOperations = new DBmodels();

	@FXML
	void selectadminfromtab(MouseEvent event) {


		Users userData = admin_update_table.getSelectionModel().getSelectedItem();
		int numU = admin_update_table.getSelectionModel().getSelectedIndex();

		if((numU-1)<-1) {
			return;
		}

		admin_username.setText(userData.getUsername());
		admin_email.setText(userData.getEmail_id());
		admin_password.setText(userData.getPassword());
		admin_name.setText(userData.getName());


	}




	private void loaduserDataToTable(TableView<Users> tableView, TableColumn<Users, String> Username,
			TableColumn<Users, String> name,
			TableColumn<Users, String> email,
			TableColumn<Users, String> Password) {

		List<Users> usersinfo = dbOperations.fetchUserdata();

		// Load data into the TableView
		ObservableList<Users> observableuserinfo = FXCollections.observableArrayList(usersinfo);
		tableView.setItems(observableuserinfo);

		// Map the columns to the properties of the Movie class
		Username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
		name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail_id()));
		Password.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
	}


	@FXML
	void deleteadminbtn() {


		dbOperations.deleteuser(admin_username.getText(),"Admin");



	}

	private void setImages() {
		// Set visibility based on the currentVisibleIndex
		img1.setVisible(currentVisibleIndex % 3 == 0);
		img2.setVisible(currentVisibleIndex % 3 == 1);
		img3.setVisible(currentVisibleIndex % 3 == 2);
	}



	@FXML
	void updateadminbtn() {

		dbOperations.UpdateUser(admin_username.getText(), admin_email.getText(), admin_password.getText(), admin_name.getText(), "Admin");



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
	void loadadminrefresh() {

		loaduserDataToTable(admin_update_table, admin_username_col, Admin_name_col, admin_email_col, admin_password_col);

	}


	@FXML
	void exit() {
		System.exit(0);
	}



	@FXML
	private void handleDashboardButton() {
		// Show/hide panes based on the button clicked
		cashdashboardpane.setVisible(true);
		scrollImgpane.setVisible(true);
		addAdminpane.setVisible(false);

		no_of_moviesvalue.setText(dbOperations.countMovies());

		Map<String, Double> summaryMap = dbOperations.getBookingSummary();

		if (summaryMap != null) {
			normal_ticket_value.setText(summaryMap.get("no_Of_Premium_tickets").toString());
			pre_ticket_value.setText(summaryMap.get("no_Of_Normal_tickets").toString());
			total_earnings_value.setText("$"+summaryMap.get("total_sum").toString());
		} else {
			System.out.println("Error retrieving booking summary");
		}



	}

	@FXML
	private void handleAddAdminButton() {
		// Show/hide panes based on the button clicked
		cashdashboardpane.setVisible(false);
		scrollImgpane.setVisible(false);
		addAdminpane.setVisible(true);
		// Hide other panes as needed


		loaduserDataToTable(admin_update_table, admin_username_col, Admin_name_col, admin_email_col, admin_password_col);
	}


}
