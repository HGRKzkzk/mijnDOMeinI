package proxy;

public class ConnectionSettings {
	
	protected int PORT = 8888;
	protected int timeout = 250;
	protected String SERVER_IP =  "172.20.10.11";
	protected String app_ID = "167";
	protected String receiver_ID = "5678";	
	
	
	public void setServerTimeOut(int ms) {
		
		this.timeout = ms;
		
		
	}
	
	public String getID() {
		
		return this.app_ID;
	}
	

}
