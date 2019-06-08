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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Device;
import model.SwitchableDevice;
import view.Main;

public class ApparatenControler implements Initializable {

	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;

	private int yOffset = -200;
	private int ySpacer = 20;
	private int ySpaceIncrease = 33;

	static String whichDevice = null;

	@FXML
	private GridPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ApplicationCommon.getFromServer();

		Main.getStage()
				.setTitle(ScreenNames.Prefix.getDescription() + "  " + ScreenNames.ApparatenView.getDescription());

		showDeviceList();

	}

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "MainScreen.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	@FXML
	protected void showApparaatToevoegen(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatToevoegen.fxml"));
		rootPane.getChildren().setAll(pane);

	}


	protected void showDeviceList() {


		for (Device device : deviceList) {

			Button deviceButton = new Button(device.getName());
			Label deviceLabel = new Label(Integer.toString(device.getPort()));

			deviceLabel.setTranslateY(yOffset + ySpacer);
			deviceLabel.setTranslateX(deviceButton.getTranslateX() - 33 );
			deviceButton.setTranslateY(yOffset + ySpacer);

			if(!device.isActivated()){

			deviceButton.getStyleClass().add("notActive");
			}

			if (device.getSwitchedOn()) {
			//	deviceLabel.getStyleClass().add("switchedOn");
			//	deviceButton.getStyleClass().add("switchedOn");

			}

			deviceButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(final ActionEvent e) {

					GridPane pane = null;
					try {

						whichDevice = deviceButton.getText();
						pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparaatDetails.fxml"));

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rootPane.getChildren().setAll(pane);

				}
			});

			rootPane.getChildren().addAll(deviceLabel);
			rootPane.getChildren().addAll(deviceButton);

			// System.out.println(device.getName());
			ySpacer += ySpaceIncrease;

		}

	}

}
