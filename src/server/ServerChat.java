package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerChat {
	ServerSocket serverSocket;
	ArrayList<Socket> users;
	Socket socket;
	Scanner scanner;

	public ServerChat() {
	}

	public ServerChat(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Ready Server ...");
			start(port);

		} catch (IOException e) {
			System.out.print("");
		} finally {

			System.out.println("Exit ServerChat ...");

		}
	}

	public void start(int port) throws IOException {
		socket = serverSocket.accept();
		users.add(socket);

		Receiver receiver = new Receiver();
		receiver.start();
		if (!socket.isConnected()) {
			System.out.println("Client Closed...");
			scanner.close();
			socket.close();
			receiver.close();
		}

		while (true) {
			System.out.println("Input Server Message...");
			scanner = new Scanner(System.in);
			String msg = scanner.nextLine();
			Sender sender = new Sender();
			Thread t = new Thread(sender);

			if (msg.equals("q")) {
				scanner.close();
				socket.close();
				sender.close();
				receiver.close();

				break;
			}

			sender.setSendMsg(msg);
			t.start();

		}

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
				try {
					String msg = null;
					msg = din.readUTF();
					System.out.println("Client = " + msg);
				} catch (IOException e) {
					System.out.print("Exit Client user....");
					break;
				}

			}
		}

	}
}
