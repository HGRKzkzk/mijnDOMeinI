package persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public List<Cluster> clusters(){
		List<Cluster> dlow = null;
		try {
		  dlow  = loadClusterList();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return dlow;
	}

	private List<Cluster> loadClusterList() throws IOException, ClassNotFoundException {
		ArrayList<Cluster> clusterlist = new ArrayList<Cluster>();
		File file = new File(name);
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		fileInputStream = new FileInputStream(file);

		objectInputStream = new ObjectInputStream(fileInputStream);
		clusterlist = (ArrayList<Cluster>) objectInputStream.readObject();
		objectInputStream.close();

		return clusterlist;
	}

}
