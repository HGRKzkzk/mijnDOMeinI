package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Device;
import model.DeviceFactory;
import model.DeviceTypes;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApparaatToevoegenControler implements Initializable {

	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;
	private DeviceFactory devicefactory = new DeviceFactory();

	@FXML
	private GridPane rootPane;
	@FXML
	private TextField deviceName;
	@FXML
	private TextField devicePort;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private Button saveButton;

	@FXML
	protected void save(ActionEvent event) throws IOException {

		String selectedType = comboBox.getValue();
		String name = deviceName.textProperty().getValue();

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

		deviceList.add(devicefactory.getDevice(selectedType, name, port, false));
		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Main.getStage()
				.setTitle(ScreenNames.Prefix.getDescription() + "  " + ScreenNames.ApparaatToevoegen.getDescription());

		for (int i = 0; i < DeviceTypes.values().length; i++) {

			String deviceDescription = DeviceTypes.values()[i].getDescription().toString();

			comboBox.getItems().add(deviceDescription);
		}

		comboBox.getSelectionModel().select(0);
		comboBox.toFront();
		deviceName.toFront();
		devicePort.toFront();
		saveButton.setDisable(checkForContent());

		deviceName.requestFocus();

		devicePort.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					devicePort.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		deviceName.textProperty().addListener((observable, oldValue, newValue) -> {
			saveButton.setDisable(!checkForContent());

		});

		devicePort.textProperty().addListener((observable, oldValue, newValue) -> {
			saveButton.setDisable(!checkForContent());

		});

	}

	public boolean checkForContent() {

		if (deviceName.getLength() >= 0 && devicePort.getLength() >= 0) {

			return true;

		}

		return false;
	}

}
