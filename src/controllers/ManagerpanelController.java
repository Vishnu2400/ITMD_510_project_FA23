package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ManagerpanelController {


	@FXML
	private AnchorPane addAdminpane;

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
	private VBox vboxmenu;

	@FXML
	void back() {

	}

	@FXML
	void next() {

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
	}


}
