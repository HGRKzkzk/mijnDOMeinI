package controller;

import java.util.Optional;

import interfaces.PortHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import persistance.SerializeHandler;

public class ApplicationCommon {

	static SerializeHandler SerializeHandler = new SerializeHandler();

	public static boolean exit() {

 
			SerializeHandler.saveAsSerializedData();
			Platform.exit();
			System.exit(0);
			return true;

	}

	static boolean possiblePort(int portn) {

		if (portn > PortHandler.maxPort) {

			System.out.println("te hoog");
			return false;

		}

		return true;
	}

}
