package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Cluster;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClusterInstellingenController implements Initializable {

	@FXML
	private GridPane rootPane;

	@FXML
	private TextField clusterName;

	protected Cluster cluster = ClusterDetailsController.cluster;
 
	private ArrayList<Cluster> clusterList = (ArrayList<Cluster>) ControllerData.clusterList;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Main.getStage()
				.setTitle(ScreenNames.Prefix.getDescription() + " " + ScreenNames.ClusterInstellingen.getDescription());

		clusterName.toFront();
		clusterName.setText(cluster.getName());

	}

	@FXML
	protected void removecluster(ActionEvent event) throws IOException {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Cluster verwijderen?");
		alert.setHeaderText("");
		alert.setContentText("Weet je zeker dat je deze cluster wilt verwijderen?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			clusterList.remove(cluster);
			GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterView.fxml"));
			rootPane.getChildren().setAll(pane);
		} else {
			return;
		}

	}

	@FXML
	protected void savecluster(ActionEvent event) throws IOException {

		cluster.changeName(clusterName.getText());

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}

}
