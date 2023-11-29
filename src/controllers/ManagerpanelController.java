package controllers;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private AnchorPane scrollImgpane;

    @FXML
    private TextField type_field;

    @FXML
    private VBox vboxmenu;

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


    @FXML
    void back() {

    }
    
    private final DBmodels dbOperations = new DBmodels();
    
    @FXML
    public void selectadminfromtab(MouseEvent event) {
    	
    	
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

    

    @FXML
    void next() {

    }

    @FXML
    void updateadminbtn() {
    	
    	dbOperations.UpdateUser(admin_username.getText(), admin_email.getText(), admin_password.getText(), admin_name.getText(), "Admin");

    }
    
    


	@FXML
	private void handleDashboardButton() {
		// Show/hide panes based on the button clicked
		cashdashboardpane.setVisible(true);
		scrollImgpane.setVisible(true);
		addAdminpane.setVisible(false);
		// Hide other panes as needed
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
