 package interfaces;

public interface Nameable {
	
	int maxNamelength = 21;
	String standardName = "Naamloos";
	
	boolean changeName(String name);
	boolean checkStringLength(String name);
	

}
