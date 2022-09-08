import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketController {
	Socket theSocket = null;
	String host = "192.168.216.81";
	BufferedReader reader;
	BufferedWriter writer;
	
	public SocketController() throws IOException {
		theSocket = new Socket(host, 5000);
		reader = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(theSocket.getOutputStream()));
	}
	
	public void sendMessage(String msg) {
		
		try {
			writer.write(msg + '\r' + '\n');
			writer.flush();
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
	}
	
	public String reciveMessage() {
		String rtnValue = null;
		
		try {
			rtnValue = reader.readLine();
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
		return rtnValue;
	}
	
	public void closeSocket() throws IOException {
		theSocket.close();
	}
}
