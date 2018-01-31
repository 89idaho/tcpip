package client;

// 구조적인것 부터 생각해야함
public class Client {
	
	public static void main(String[] args) throws Exception {
		String ip = "70.12.111.136";
		int port = 8888;
		ClientChat chat = null;		
		chat =  new ClientChat(ip, port);	
	}

}
