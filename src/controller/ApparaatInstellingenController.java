package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Device;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ApparaatInstellingenController implements Initializable {

	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;

	@FXML
	private GridPane rootPane;

	@FXML
	private TextField deviceName;

	@FXML
	private TextField devicePort;

	@FXML
	private Button saveButton;

	@FXML
	private Button removeButton;

	@FXML
	private Button deactivateButton;

	private Device device = ApparaatDetailsController.getDevice();

	@FXML
	protected void removedevice(ActionEvent event) throws IOException {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Apparaat verwijderen?");
		alert.setHeaderText("");
		alert.setContentText("Weet je zeker dat je dit apparaat wilt verwijderen?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			deviceList.remove(device);
			GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenView.fxml"));
			rootPane.getChildren().setAll(pane);
		} else {
			return;
		}

	}

	@FXML
	protected void savedevice(ActionEvent event) throws IOException {

		int port = 0;

		try {
			port = Integer.parseInt(devicePort.textProperty().getValue());
		} catch (NumberFormatException e) {
			System.out.println("not a number");
			return;
		}

		if (!ApplicationCommon.possiblePort(port)) {
			return;
		}

		device.changeName(deviceName.textProperty().getValue());
		device.changePort(Integer.parseInt(devicePort.textProperty().getValue()));

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void deactivate(ActionEvent event) throws IOException {
		device.setActivated(!device.isActivated());
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatInstellingen.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Main.getStage()
				.setTitle(ScreenNames.Prefix.getDescription() + " " +  ScreenNames.ApparaatInstellingen.getDescription());

		if (!device.isActivated()) {

			deviceName.setDisable(true);
			devicePort.setDisable(true);
			removeButton.setDisable(true);
			saveButton.setDisable(true);
			deactivateButton.setText("Activeren");

		}

		deviceName.toFront();
		devicePort.toFront();

		devicePort.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					devicePort.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		deviceName.setText(device.getName());
		devicePort.setText(String.valueOf(device.getPort()));
		// TODO Auto-generated method stub

	}

}
