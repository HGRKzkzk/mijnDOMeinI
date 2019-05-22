package model;

public enum DeviceTypes {
	
	   READONLY("Alleen uitleesbaar"),
	   SWITCHABLE("Schakelbaar"),
	   DIMMABLE("Dimbaar");
	    
	    
	    private final String description;  
	    
	    DeviceTypes(String description) {
	        this.description = description;
	       
	    }

		public String getDescription() {
			return description;
		}

}
