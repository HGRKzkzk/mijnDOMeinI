package controller;

import interfaces.Dimmable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Device;
import model.DimmableDevice;
import model.SwitchableDevice;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApparaatDetailsController implements Initializable {

	@FXML
	private Pane rootPane;
	@FXML
	private Label deviceName;
	@FXML
	private Label devicePort;
	@FXML
	private Label deviceState;
	@FXML
	private Label deviceActive;
	@FXML
	private Button buttonOn;
	@FXML
	private Slider slider;
	@FXML
	private Button buttonOff;
	@FXML
	private Label deviceType;

	public void setStageTitle(String newTitle) {
		Main.getStage().setTitle(newTitle);
	}

	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;
	protected DimmableDevice dm;
	private static Device device;

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenView.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void showDeviceSettings(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatInstellingen.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Main.getStage()
				.setTitle(ScreenNames.Prefix.getDescription() + "  " + ScreenNames.ApparaatDetails.getDescription());

		buttonOn.setVisible(false);
		buttonOff.setVisible(false);
		slider.setVisible(false);

		deviceName.setText(ApparatenControler.whichDevice);

		for (Device device : deviceList) {

			if (device.getName() == ApparatenControler.whichDevice) {
				this.setDevice(device);
				this.devicePort.setText(String.valueOf(device.getPort()));

				if (device.isActivated()) {

					if (device instanceof Dimmable) {
						dm = (DimmableDevice) device;
						slider.setVisible(true);
						slider.setValue(((Dimmable) device).getDimValue());
						// slider.setShowTickLabels(true);

						slider.valueProperty().addListener((observable, oldValue, newValue) -> {
							
							dm.setDimValue(newValue.intValue());

						});

					}

					if (device instanceof SwitchableDevice || device instanceof Dimmable) {
						buttonOn.setVisible(true);
						buttonOff.setVisible(true);

					}

					if (!device.getSwitchedOn()) {

						slider.setVisible(false);

					}

				}
			}

		}

		deviceType.setText(getDevice().getClass().getSimpleName() + "   (" +  getDevice().getClass().getSuperclass().getSimpleName() + ")"); 
				
		deviceState.setText(String.valueOf(getDevice().getSwitchedOn()));
		deviceActive.setText(String.valueOf(getDevice().isActivated()));
		buttonOn.setDisable(getDevice().getSwitchedOn());
		buttonOff.setDisable(!getDevice().getSwitchedOn());

	}

	@FXML
	protected void switchOn(ActionEvent event) throws IOException {
		 getDevice().setSwitchedOn(true);

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void switchOff(ActionEvent event) throws IOException {

		 getDevice().setSwitchedOn(false);

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatDetails.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	public void backToApparatenView() {

	}

	public static Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		ApparaatDetailsController.device = device;
	}

}
