 package interfaces;

public interface Nameable {
	
	int maxNamelength = 21;
	String standardName = "Naamloos";
	
	void changeName(String name);
	boolean validateName(String name);
	

}
