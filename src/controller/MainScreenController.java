package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Cluster;
import model.Device;
import persistance.SerializeHandler;
import view.Main;

public class MainScreenController implements Initializable {

	SerializeHandler SerializeHandler = new SerializeHandler();
	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;
	private ArrayList<Cluster> clusterList = (ArrayList<Cluster>) ControllerData.clusterList;

	@FXML
	private GridPane rootPane;
	@FXML
	private Button devices;
	@FXML
	private Button clusters;
	@FXML
	private Label welcomeUser;

	@FXML
	protected void apparatenKnop(ActionEvent event) throws IOException {
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenView.fxml"));
		rootPane.getChildren().setAll(pane);
		
		 


	}

	@FXML
	protected void clusterKnop(ActionEvent event) throws IOException {
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void mainSettings(ActionEvent event) throws IOException {
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "SettingsView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void exit(ActionEvent event) throws IOException {

		ApplicationCommon.exit();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	 
		
		
		Main.getStage().setTitle(ScreenNames.Prefix.getDescription());
		devices.setText("Apparaten (" + deviceList.size() + ")");
		clusters.setText("Clusters (" + clusterList.size() + ")");

	}

}
