package jserial;

public enum ArduinoRequests {
	getStatus(0),
	switchPin(1),
	setValue(3);
	
	
	public int num;

	ArduinoRequests(int num){
		   this.num = num;
	}

}
