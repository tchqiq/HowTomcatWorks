import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by heqi02 on 16/2/16.
 */
public class HttpServer {
    /**
     * WEB_ROOT is the directory where our HTML and other files reside.
     * For this package, WEB_ROOT is the "webroot" directory under the working
     * directory.
     * The working directory is the location in the file system
     * from where the java command was invoked.
     */
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    //shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    private void await() {
        ServerSocket serverSocket = null;
        int port = 8866;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //Loop waiting for the request
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                //create Request Object and parse
                Request request = new Request(input);
                request.parse();

                Response respone = new Response(output);
                respone.setRequest(request);
                respone.sendStaticResource();

                //close the socket
                socket.close();

                //check if the previous URI is a shutdown command
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }

    }
}
