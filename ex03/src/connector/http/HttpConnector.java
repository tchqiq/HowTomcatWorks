package connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by heqi02 on 16/2/20.
 */
public class HttpConnector implements Runnable {
    boolean stopped;
    private String sheme = "http";

    public String getSheme() {
        return sheme;
    }


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int port = 8866;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!stopped) {
            // Accept the next incoming connection from the server socket
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                continue;
            }

            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);

        }

    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
