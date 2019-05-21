 package view;

import java.io.IOException;
import controller.ApplicationCommon;
import controller.ControllerData;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.GebruikersApplicatie;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	public final static String FXMLLocation = "/view/";
	private static Stage stage = new Stage();
	private int horizontalresolution = 800;
	private int verticalresolution = 600;
	private String cssFile = "application.css";
	private String title = "mijnD0Mein  >> ";

	private static GebruikersApplicatie gebruikersApplicatie = new GebruikersApplicatie();

	public void start(Stage primaryStage) {

		ControllerData.init();

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root, horizontalresolution, verticalresolution);
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
			stage = primaryStage;
			primaryStage.show();

		} catch (IOException e1) {

		}

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				gebruikersApplicatie.getDcom().pushConfigToServer();
				ApplicationCommon.exit();

			}
		});

	}

	public static void main(String[] args) throws InterruptedException {
		launch(args);

	}

	public static GebruikersApplicatie getGa() {
		return gebruikersApplicatie;
	}

	public static void setGa(GebruikersApplicatie ga) {
		Main.gebruikersApplicatie = ga;
	}
	
	public static Stage getStage() {
		return stage;
	}

}
