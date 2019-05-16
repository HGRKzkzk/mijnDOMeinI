package controller;

public enum ScreenNames {
	
	
	Prefix("mijnD0Mein  >>"), 
	ApparaatDetails("Apparaat details"),
	ApparaatInstellingen("Apparaat instellingen"),
	ApparaatToevoegen("Apparaat toevoegen"),
	ApparatenView("Apparaten"),
	ClusterDetails("Cluster details"),
	ClusterInstellingen("Cluster instellingen"),
	ClusterToevoegen("Cluster toevoegen"),
	ClusterView("Clusters"),
	MainScreen(""),
	Settings("Instellingen");
	
    private final String description;  
    
    ScreenNames(String description) {
        this.description = description;
       
    }
    
	public String getDescription() {
		return description;
	}
	
	
	

}
