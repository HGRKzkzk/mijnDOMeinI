package interfaces;

public interface Dimmable extends Switchable {
	
	
	int minValue = 0;
	
	
	void setDimValue(int newvalue);
	int getDimValue();
	boolean validateDimvalue();

}
