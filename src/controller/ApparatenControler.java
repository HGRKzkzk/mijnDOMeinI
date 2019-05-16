package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Device;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApparatenControler implements Initializable {

	private ArrayList<Device> deviceList = (ArrayList<Device>) ControllerData.deviceList;

	static String whichDevice = null;

	@FXML
	private GridPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
	
	
	
	@FXML
	protected void showApparaatVerwijderen(ActionEvent event) throws IOException {
		
		

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "ApparatenVerwijderen.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	
	
	
	protected void showDeviceList() {
		

		int i = 20;
		for (Device device : deviceList) {
			
			

			Button deviceButton = new Button(device.getName());
			deviceButton.setTranslateY(-200 + i);
			
			if (device.getSwitchedOn()) {
				
				deviceButton.getStyleClass().add("switchedOn");
				
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

			rootPane.getChildren().addAll(deviceButton);

			// System.out.println(device.getName());
			i += 33;

		}
		
 
		
	}

}
