package controller;

import java.util.Optional;

import interfaces.PortHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.GebruikersApplicatie;
import persistance.SerializeHandler;
import view.Main;

public class ApplicationCommon {

	static SerializeHandler SerializeHandler = new SerializeHandler();
	static GebruikersApplicatie ga = Main.getGa();

	public static boolean exit() {

 
			SerializeHandler.saveAsSerializedData();
			pushToServer();
			Platform.exit();
			System.exit(0);
			return true;

	}
	
	
	public static void pushToServer() {
		
		 
		ga.getDcom().pushConfigToServer();
	}
	
public static void getFromServer() {
		
		ga.getDcom().requestConfigFromServer();
	}
	

	static boolean possiblePort(int portn) {

		if (portn > PortHandler.maxPort) {

			System.out.println("te hoog");
			return false;

		}

		return true;
	}

}
