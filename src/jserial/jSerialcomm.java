
package jserial;

import com.fazecast.jSerialComm.*;

import java.io.UnsupportedEncodingException;

public class jSerialcomm {

	private static SerialPort comPort;
	private final static int baudRate = 57600;
	private final static int dataBits = 8;
	private final static int stopBits = 1;
	private final static int protocolSleep = 17;
	private final static int dataAvailSleep = 11;

	public static String response;

	private final static String BOM = "<";
	private final static String EOM = ">";
	private final static String DIVIDER = ",";
	
	public static void addListeners() {

		// data available listener
		comPort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() {

				return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;

			}

			@Override
			public void serialEvent(SerialPortEvent event) {

				if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {

					// Thread net lang genoeg laten slapen om de output buffer van de arduino vol te
					// laten lopen
					try {
						Thread.sleep(dataAvailSleep);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					if (comPort.bytesAvailable() > 0) {

						byte[] newData = new byte[comPort.bytesAvailable()];
						comPort.readBytes(newData, newData.length);

						String msgString = null;

						try {

							msgString = new String(newData, "UTF-8");
							System.out.println("Arduino: " + msgString);
							response = msgString;

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

					}

				}
				return;
			}

		});

	}

	public static boolean connect() {

		SerialPort[] comPorts = SerialPort.getCommPorts();
		for (SerialPort poort : comPorts) {
			if (poort.getDescriptivePortName().contains("Arduino Uno"))
				;
			// Arduino found
			comPort = poort;

		}

		if (comPort != null && !comPort.isOpen()) {
			
			comPort.openPort();
			comPort.setComPortParameters(baudRate, dataBits, stopBits, SerialPort.NO_PARITY);
			// Port opened
			addListeners();
			System.out.println("(Arduino) Port openend succesfully");
			return true;
		}

		else {
			// Port cannot be openend
			return false;
		}

	}

	public static boolean sendProtocolData(String pString) {

		byte[] bytesToWrite = pString.getBytes();
		if (comPort != null) {
			comPort.writeBytes(bytesToWrite, bytesToWrite.length);
			// System.out.println(pString);
			return true;
		} else
			return false;

	}

	public static boolean sendProtocolData(int pin, int bank, int action) {

		String pString = BOM + pin + DIVIDER + bank + DIVIDER + action + EOM;
		byte[] bytesToWrite = pString.getBytes();
		if (comPort != null) {
			comPort.writeBytes(bytesToWrite, bytesToWrite.length);
			// System.out.println(pString);
			return true;
		} else
			return false;

	}

//	public static StatusMessage giveStatus() {
//
//		String toSplit = response;
//		toSplit = toSplit.substring(startChars.length(), toSplit.length() - endChars.length());
//		String[] values = toSplit.split(",");
//		int val1 = Integer.parseInt(values[0]);
//		int val2 = Integer.parseInt(values[1]);
//
//		return new StatusMessage(val1, val2);
//
//	}

	public static void close() {
		if (comPort != null) {
			comPort.closePort();
		}
		return;
	}

	public static boolean requestInitialStatus(int pin, int bank, int action) {

		String pString = BOM + pin + DIVIDER + bank + DIVIDER + action + EOM;
		byte[] bytesToWrite = pString.getBytes();
		if (comPort != null) {
			comPort.writeBytes(bytesToWrite, bytesToWrite.length);
			try {
				Thread.sleep(protocolSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return true;
		} else
			return false;

	}

}
