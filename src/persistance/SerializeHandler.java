package persistance;

import model.Cluster;
import model.Device;
import view.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeHandler {
	
	private ArrayList<Device> deviceList = new ArrayList<Device>();
	private ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
	

	public void saveAsSerializedData() {
		
		deviceList = (ArrayList<Device>) Main.getGa().getDeviceList();
	 	clusterList = (ArrayList<Cluster>) Main.getGa().getClusterList();

		// saveDeviceList();
		// saveClusterList();

	}

	public void saveDeviceList() {
		
		
		String naam = "devices";
		File file = new File(naam);

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(deviceList);
			objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void saveClusterList() {
		
		String naam = "clusters";
		File file = new File(naam);

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(clusterList);
			objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void loadFromSerializedData() throws FileNotFoundException, InvalidClassException, ClassNotFoundException {

		// loadClusterList();
		// loadDeviceList();

	}

	@SuppressWarnings("unchecked")
	public List<Cluster> loadClusterList()
			throws FileNotFoundException, ClassNotFoundException, InvalidClassException {
		ArrayList<Cluster> clusterlist = new ArrayList<Cluster>();
		String naam = "clusters";
		File file = new File(naam);
		FileInputStream fileInputStream = null;
		fileInputStream = new FileInputStream(file);

		ObjectInputStream objectInputStream = null;

		try {
			objectInputStream = new ObjectInputStream(fileInputStream);
			clusterlist = (ArrayList<Cluster>) objectInputStream.readObject();
			return clusterlist;

		} catch (IOException e) {
			// e.printStackTrace();
			throw new InvalidClassException(null);

		} finally {

			try {
				objectInputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Dit zou nooit voor moeten kunnen komen", e);

			}

		}

	}

	@SuppressWarnings("unchecked")
	public List<Device> loadDeviceList() {
		ArrayList<Device> deviceList = new ArrayList<Device>();
		String naam = "devices";
		File file = new File(naam);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
				
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		ObjectInputStream objectInputStream = null;

		try {

			objectInputStream = new ObjectInputStream(fileInputStream);
			deviceList = (ArrayList<Device>) objectInputStream.readObject();
			return deviceList;

		} catch (IOException e) {
			// e.printStackTrace();
			try {
				throw new InvalidClassException(null);
			} catch (InvalidClassException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				objectInputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Dit zou nooit voor moeten kunnen komen", e);

			}

		}

		return deviceList;

	}

}
