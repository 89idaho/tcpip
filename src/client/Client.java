package client;

// �������ΰ� ���� �����ؾ���
public class Client {
	
	public static void main(String[] args) throws Exception {
		String ip = "70.12.111.136";
		int port = 8888;
		ClientChat chat = null;		
		chat =  new ClientChat(ip, port);	
	}

}
