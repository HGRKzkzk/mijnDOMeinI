package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Cluster;
import model.Device;
import view.Main;

public class ClusterDetailsController implements Initializable {
	
	
	@FXML private Button buttonOn;
	@FXML private Button buttonOff;
	
	@FXML private Label clusterName;
	@FXML private Label clusterContents;
	@FXML private Label clusterState;
	
	
	@FXML private Pane rootPane;

	static String whichCluster = null;
	
	 private ArrayList<Cluster> clusterList = (ArrayList<Cluster>) ControllerData.clusterList;

	protected static Cluster cluster;

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterView.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	
	
	@FXML
	protected void showClusterSettings(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterInstellingen.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	
	

	
	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Main.getStage()
		.setTitle(ScreenNames.Prefix.getDescription() + "  " + ScreenNames.ClusterDetails.getDescription());
				
		for (Cluster cluster : clusterList) {
			
			if (cluster.getName() == ClusterController.whichCluster) {
				
				this.cluster = cluster;				
				

			}
			
			buttonOn.setDisable(cluster.isSwitchedOn());
			buttonOff.setDisable(!cluster.isSwitchedOn()); 

		}
		
		clusterName.setText(String.valueOf("   " + cluster.getName()));
		clusterContents.setText(cluster.giveClusterContentsAsString() + ".");
		clusterState.setText(String.valueOf("   " + cluster.isSwitchedOn()));
		
		
		
		

	}

	@FXML
	protected void switchOn(ActionEvent event) throws IOException {
	
		cluster.switchOn();
		
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	
	@FXML
	protected void switchOff(ActionEvent event) throws IOException {

		
		cluster.switchOff();
		
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	
	public void requestStatus() throws IOException {
		
		cluster.requestCurrentValue();
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterDetails.fxml"));
		rootPane.getChildren().setAll(pane);
		

	}
	
	
	public void backToApparatenView() {
		
		
	}

}
