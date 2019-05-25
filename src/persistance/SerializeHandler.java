package persistance;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Cluster;
import view.Main;

public class SerializeHandler {

	String name = "clusters";

	private ArrayList<Cluster> clusterList = new ArrayList<Cluster>();

	public void saveAsSerializedData() {
		clusterList = (ArrayList<Cluster>) Main.getGa().getClusterList();

		try {
			saveClusterList();
		} catch (IOException e) {

		}
	}

	private void saveClusterList() throws IOException {

		File file = new File(name);
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		fileOutputStream = new FileOutputStream(file);
		objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(clusterList);

		objectOutputStream.flush();
		objectOutputStream.close();

	}

	public void loadFromSerializedData()   {

		try {
			loadClusterList();
		} catch (ClassNotFoundException | IOException e) {

		}

	}

	public List<Cluster> clusters() throws IOException, ClassNotFoundException {

		return loadClusterList();


	}

	private List<Cluster> loadClusterList() throws IOException, ClassNotFoundException, FileNotFoundException {
		ArrayList<Cluster> clusterlist = new ArrayList<Cluster>();
		File file = new File(name);
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		fileInputStream = new FileInputStream(file); //TODO file not found netjes afvangen


		objectInputStream = new ObjectInputStream(fileInputStream);
		clusterlist = (ArrayList<Cluster>) objectInputStream.readObject();
		objectInputStream.close();

		for (Cluster cl : clusterList){
			cl.getdCom().setProxy();
		}
		return clusterlist;
	}

}
