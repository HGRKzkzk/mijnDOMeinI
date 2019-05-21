package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.Main;

public class SettingsController implements Initializable {

	@FXML
	private GridPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Main.getStage().setTitle(ScreenNames.Prefix.getDescription() + "  " + ScreenNames.Settings.getDescription());

	}

	@FXML
	protected void showConsole(ActionEvent event) throws IOException {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.FXMLLocation + "Console.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

 

	@FXML
	protected void back(ActionEvent event) throws IOException {

		GridPane pane = FXMLLoader.load(getClass().getResource(Main.FXMLLocation + "MainScreen.fxml"));
		rootPane.getChildren().setAll(pane);

	}

}
