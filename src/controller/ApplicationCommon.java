package controller;

import interfaces.PortHandler;
import javafx.application.Platform;
import jserial.jSerialcomm;
import persistance.SerializeHandler;

public class ApplicationCommon {

	static SerializeHandler SerializeHandler = new SerializeHandler();

	public static boolean exit() {

 
			SerializeHandler.saveAsSerializedData();
			jSerialcomm.close();
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
