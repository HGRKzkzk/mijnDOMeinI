package persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Device;
import model.Cluster;
import view.Main;

public class SerializeHandler {
	
	private ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
	

	public void saveAsSerializedData() {
		clusterList = (ArrayList<Cluster>) Main.getGa().getClusterList();
		saveClusterList();
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

		loadClusterList();


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
			e.printStackTrace();
			throw new InvalidClassException(null);

		} finally {

			try {
				objectInputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Dit zou nooit voor moeten kunnen komen", e);

			}

		}

	}



}
