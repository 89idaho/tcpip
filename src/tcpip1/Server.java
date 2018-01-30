package tcpip1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream in = null;
		InputStreamReader inr = null;
		BufferedReader br = null;

		// 포트는 1000이상으로 설정
		try {

			serverSocket = new ServerSocket(7777);
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println(ia.getHostName());
			System.out.println(ia.getHostAddress());
			System.out.println("Start Server ...");
			
			while(true) {
				socket = serverSocket.accept(); // Auto wait... Socket이 연결되면 데이터를 주고 받을 수 있다.
				System.out.println("Client Connected..");	
				// receive Data
				in = socket.getInputStream();
				inr = new InputStreamReader(in);
				br = new BufferedReader(inr);
				String reciveStr = br.readLine();
				System.out.println(reciveStr);				
				System.out.println("End Server...");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				br.close();
				inr.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
