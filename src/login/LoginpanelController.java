
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

/**
 *
 * @author amir
 */
public class FXMLDocumentController implements Initializable {

	@FXML
	private AnchorPane pane_login;

	@FXML
	private TextField txt_username;

	@FXML
	private PasswordField txt_password;

	@FXML
	private ComboBox type;

	@FXML
	private Button btn_login;

	@FXML
	private AnchorPane pane_signup;

	@FXML
	private TextField txt_username_up;

	@FXML
	private TextField txt_password_up;

	@FXML
	private TextField email_up;

	@FXML
	private ComboBox  type_up;
	
	@FXML
	private Button clsbtn1;

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public void LoginpaneShow(){

		pane_login.setVisible(true);
		pane_signup.setVisible(false);
	}

	public void SignuppaneShow(){

		pane_login.setVisible(false);
		pane_signup.setVisible(true);
	}


	@FXML  
	private void Login (ActionEvent event) throws Exception{  
		//    conn = mysqlconnect.ConnectDb();
//		String sql = "Select * from users where username = ? and password = ? and type = ? ";
		try {
//			pst = conn.prepareStatement(sql);
//			pst.setString(1, txt_username.getText());
//			pst.setString(2, txt_password.getText());
//			pst.setString(3, type.getValue().toString());
//
//			rs = pst.executeQuery();

			if(type.getValue().toString()=="Admin" /*&& rs.next()*/) {

				JOptionPane.showMessageDialog(null, "welcome Admin");
				btn_login.getScene().getWindow().hide();
				Parent root = FXMLLoader.load(getClass().getResource("APanel.fxml"));
				Stage mainStage = new Stage();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("ui.css").toExternalForm());
				mainStage.setScene(scene);
				mainStage.show();


			}else if(type.getValue().toString()=="User" /*&& rs.next()*/){ 
				JOptionPane.showMessageDialog(null, "Username And Password is Corect");

				btn_login.getScene().getWindow().hide();
				Parent root = FXMLLoader.load(getClass().getResource("CPanel.fxml"));
				Stage mainStage2 = new Stage();
				Scene scene = new Scene(root);
				mainStage2.setScene(scene);
				mainStage2.show();
			}else
				JOptionPane.showMessageDialog(null, "Invalide Username Or Password");}
		 catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			}
		}
//		}
//
//	}
	
	 	@FXML
	    private void exit(ActionEvent event) {
	        // Get the source of the event, which is the button
	        System.exit(0);
	    }


	public void add_users(ActionEvent event){    
		//        conn = mysqlconnect.ConnectDb();
		String sql = "insert into users (username,password,type,email) values (?,?,?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_username_up.getText());
			pst.setString(2, txt_password_up.getText());
			pst.setString(3, type_up.getValue().toString());
			pst.setString(4, email_up.getText());
			pst.execute();

			JOptionPane.showMessageDialog(null, "Saved");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}  
	}



	@Override
	public void initialize(URL url, ResourceBundle rb) {
		type_up.getItems().addAll("Client");
		type.getItems().addAll("Admin","User");
	}    

}
