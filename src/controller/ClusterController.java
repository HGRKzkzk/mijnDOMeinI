package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Cluster;
import model.Device;
import view.Main;

public class ClusterController implements Initializable {

	private ArrayList<Cluster> clusterList = (ArrayList<Cluster>) ControllerData.clusterList;
	static String whichCluster = null;

	@FXML
	private GridPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Main.getStage()
		.setTitle(ScreenNames.Prefix.getDescription() + "  " + ScreenNames.ClusterView.getDescription());

		showDeviceList();

	}

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "MainScreen.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void showClusterToevoegen(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterToevoegen.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	protected void showDeviceList() {


		int i = 20;
		for (Cluster cluster : clusterList) {

			
			Button clusterButton = new Button(cluster.getName());
			clusterButton.setTranslateY(-200 + i);

			if (cluster.isSwitchedOn()) {

				clusterButton.getStyleClass().add("switchedOn");

			}

			clusterButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {

					GridPane pane = null;
					try {

						whichCluster = clusterButton.getText();
						pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterDetails.fxml"));

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rootPane.getChildren().setAll(pane);

				}
			});

			rootPane.getChildren().addAll(clusterButton);

			// System.out.println(device.getName());
			i += 33;

		}

	}

}
