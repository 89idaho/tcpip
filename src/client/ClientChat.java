package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
	// 멤버변수
	String ip;
	int port;
	Scanner scanner;
	Socket socket;

	// 생성자
	public ClientChat() {
	}

	public ClientChat(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			socket = new Socket(ip, port);
			System.out.println("Connected Server ...");
			start();
			if (!socket.isConnected()) {
				System.out.println("Server Closed...");
				scanner.close();
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Connection Refused ...");
		}

	}

	public void start() throws IOException {
		scanner = new Scanner(System.in);
		Receiver receiver = new Receiver();
		receiver.start();

		while (true) {
			System.out.println("Input Client Message ...");
			String msg = scanner.nextLine();
			Sender sender = new Sender();
			Thread t = new Thread(sender);

			if (msg.equals("end")) {
				receiver.close();
				sender.close();
				socket.close();
				scanner.close();
				break;
			}

			sender.setSendMsg(msg);
			t.start();

		}
		System.out.println("Exit Chatting ...");
	}

	// Message Sender ..................................
	class Sender implements Runnable {

		OutputStream out;
		DataOutputStream dout;
		String msg;

		public Sender() throws IOException {
			out = socket.getOutputStream();
			dout = new DataOutputStream(out);
		}

		public void setSendMsg(String msg) {
			this.msg = msg;
		}

		public void close() throws IOException {
			dout.close();
			out.close();
		}

		@Override
		public void run() {
			try {
				if (dout != null) {
					dout.writeUTF(msg);
				}
			} catch (IOException e) {
				System.out.println("Not Available");
			}

		}

	}

	// Message Receiver..................................
	class Receiver extends Thread {
		InputStream in;
		DataInputStream din;

		public Receiver() throws IOException {
			in = socket.getInputStream();
			din = new DataInputStream(in);
		}

		public void close() throws IOException {
			din.close();
			in.close();
		}

		@Override
		public void run() {
			while (true) {
				String msg = null;
				try {					
					msg = din.readUTF();
					System.out.println("Server = " + msg);

				} catch (IOException e) {
					System.out.print("Exit Server ....");
					break;
				}

			}
		}

	}

}
