package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Cuiys
 * @ClassName:ServerClientConnectionStream
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 公司名称
 * @date 2018年1月18日 上午11:11:36
 */
public class ServerClientConnectionStream {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public ServerClientConnectionStream(Socket socket) {
		super();
		this.socket = socket;
		getStream();
	}

	private void getStream() {
		try {
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String message) {
		writer.println(message);
		writer.flush();
	}

	public String read() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
