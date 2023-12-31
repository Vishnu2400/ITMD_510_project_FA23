

package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.DBmodels;


public class LoginpanelController implements Initializable {

	@FXML
	private AnchorPane pane_login;


	@FXML
	private AnchorPane forgotpasswordpane; 

	@FXML
	private HBox  lgnsignupPane;


	@FXML
	private TextField txt_username;

	@FXML
	private PasswordField txt_password;

	@SuppressWarnings("rawtypes")
	@FXML
	private ComboBox type;

	@FXML
	private Button btn_login;

	@FXML
	private Button logminimizebtn;

	@FXML
	private AnchorPane pane_signup;

	@FXML
	private TextField txt_name_up1;

	@FXML
	private TextField txt_username_up;

	@FXML
	private TextField txt_password_up;

	@FXML
	private TextField email_up;

	@FXML
	private TextField updt_uname;

	@FXML
	private PasswordField updt_cpassword;

	@FXML
	private PasswordField updt_password;

	@FXML
	private AnchorPane forgotpane;

	@SuppressWarnings("rawtypes")
	@FXML
	private ComboBox  type_up;

	@FXML
	private Button clsbtn1;

	private String usernamefromtxt;

	@FXML
	private Label infoLabel;

	String usern;
	String cpassword ;
	String Updtpassword ;


	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;


	private final DBmodels dbOperations = new DBmodels();



	public void LoginpaneShow(){

		pane_login.setVisible(true);
		pane_signup.setVisible(false);
		forgotpasswordpane.setVisible(false);
		lgnsignupPane.setVisible(true);
	}

	public void SignuppaneShow(){

		pane_login.setVisible(false);
		pane_signup.setVisible(true);
		forgotpasswordpane.setVisible(false);
		lgnsignupPane.setVisible(true);
	}

	//Forgot Password

	@FXML
	void forgotpasshl() {

		forgotpasswordpane.setVisible(true);
		pane_login.setVisible(false);
		pane_signup.setVisible(false);
		lgnsignupPane.setVisible(false);

	}


	//updating the password
	public void updatepassword_btn() {
		usern = updt_uname.getText();
		cpassword = updt_cpassword.getText();
		Updtpassword = updt_password.getText();

		Platform.runLater(() -> {
			while (!cpassword.equals(Updtpassword)) {
				infoLabel.setText("Incorrect password. Please re-enter.");

				cpassword = updt_cpassword.getText();
				Updtpassword = updt_password.getText();
			}

		});

		// Call the updatePassword method
		dbOperations.updatePassword(usern, cpassword);

		updt_uname.clear();
		updt_cpassword.clear();
		updt_password.clear();

		LoginpaneShow();
	}


	@FXML
	void backtologin() {

		LoginpaneShow();

	}

	//login choose type
	@FXML  
	private void Login(ActionEvent event) throws Exception {  
		String userType = type.getValue().toString();
		usernamefromtxt = txt_username.getText().toString();
		if (dbOperations.loginUser(txt_username.getText(), txt_password.getText(), userType)) {
			switch(userType) {
			case("Admin"):
				JOptionPane.showMessageDialog(null, "Welcome Admin");
			btn_login.getScene().getWindow().hide();
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/views/Adminpanel.fxml"));
				Stage mainStage = new Stage();
				mainStage.initStyle(StageStyle.UNDECORATED);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/views/ui.css").toExternalForm());
				mainStage.setScene(scene);
				mainStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

			case("User"):
				JOptionPane.showMessageDialog(null, "Username And Password are Correct");
			try {
				btn_login.getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Userpanel.fxml"));
				Parent root1 = loader.load();
				Userpanelcontroller upanelcontroller = loader.getController();
				upanelcontroller.setUsername(usernamefromtxt);
				Stage mainStage2 = new Stage();
				mainStage2.initStyle(StageStyle.UNDECORATED);
				Scene scene1 = new Scene(root1);
				mainStage2.setScene(scene1);
				mainStage2.show();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;

			case("Manager"):
				JOptionPane.showMessageDialog(null, "Welcome Manager");
			try {
				btn_login.getScene().getWindow().hide();
				Parent root11 = FXMLLoader.load(getClass().getResource("/views/Managerpanel.fxml"));
				Stage mainStage3 = new Stage();
				mainStage3.initStyle(StageStyle.UNDECORATED);
				Scene scene11 = new Scene(root11);
				mainStage3.setScene(scene11);
				mainStage3.show();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
			}    
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Username Or Password");
		}
	}





	@FXML
	void exit() {
		// Get the source of the event, which is the button
		System.exit(0);
	}


	//Adding users
	@FXML
	private void add_users(ActionEvent event) {
		dbOperations.addUser(txt_username_up.getText(), email_up.getText(), txt_password_up.getText(), txt_name_up1.getText(),
				type_up.getValue().toString());
	}



	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		type_up.getItems().addAll("User");
		type.getItems().addAll("Admin","User","Manager");
		pane_login.setVisible(true);
		pane_signup.setVisible(false);

		logminimizebtn.setOnAction(e -> {
			((Stage) logminimizebtn.getScene().getWindow()).setIconified(true);
		});

		forgotpasswordpane.setVisible(false);
		lgnsignupPane.setVisible(true);
	}  


}
