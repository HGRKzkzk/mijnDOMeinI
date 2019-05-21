package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import interfaces.Nameable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import model.Cluster;
import model.Device;
import model.SwitchableDevice;
import view.Main;

public class ClusterToevoegenController implements Initializable {

	@FXML
	private GridPane rootPane;

	@FXML
	private Button saveButton;

	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;
	private ArrayList<Cluster> clusterList = (ArrayList<Cluster>) ControllerData.clusterList;


	protected ArrayList<Device> selectedDeviceList = new ArrayList<Device>();

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void saveCluster(ActionEvent event) throws IOException {

		String name = Nameable.standardName;
		TextInputDialog dialog = new TextInputDialog("Cluster " + clusterList.size());
		dialog.setTitle("Clusternaam");
		dialog.setHeaderText("");
		dialog.setContentText("Geef deze cluster een naam:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			name = result.get();
		}
		 else {
			 return;
			}

		
		Cluster cluster = new Cluster(name);
		for (Device device : selectedDeviceList) {
			cluster.addDeviceToCluser(deviceList.get
					(deviceList.indexOf(device)));
		}

		clusterList.add(cluster);

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ClusterView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Main.getStage()
		.setTitle(ScreenNames.Prefix.getDescription() + " " +   ScreenNames.ClusterToevoegen.getDescription());

		checkAmmount();
		showDeviceList();

	}

	public void checkAmmount() {
 
		if (selectedDeviceList.size() == 1 || selectedDeviceList.size() == 0   ) {
			saveButton.setDisable(true);
		}

		else {
			saveButton.setDisable(false);
		}

	}

	protected void showDeviceList() {

		int i = 20;
		for (Device device : deviceList) {

			Button deviceButton = new Button(device.getName());
			deviceButton.setTranslateY(-200 + i);

			if (device.getSwitchedOn()) {

				// deviceButton.getStyleClass().add("switchedOn");

			}

			deviceButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {

					if (!selectedDeviceList.contains(device)) {

						deviceButton.getStyleClass().add("selected");
						selectedDeviceList.add(device);
						checkAmmount();

					}

					else if (selectedDeviceList.contains(device)) {

						deviceButton.getStyleClass().remove("selected");
						selectedDeviceList.remove(device);
						checkAmmount();

					}

				}
			});

			rootPane.getChildren().addAll(deviceButton);

			// System.out.println(device.getName());
			i += 33;

		}

	}

}
