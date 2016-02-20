import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by heqi02 on 16/2/17.
 */
public class HttpServer1 {

    //shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    //the shutdown command received
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 server = new HttpServer1();
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

        // Loop waitting for a request
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                //create Requst object and parse
                Request request = new Request(input);
                request.parse();

                //create Response object
                Response response = new Response(output);
                response.setRequest(request);

                // check if this is a request for a servlet or a static resource
                // a request for a servlet begins with "/servlet/"
                if (request.getUri().startsWith("/servlet")) {
                    ServletProcessor1 processor = new ServletProcessor1();
                    processor.process(request, response);
                }
                else {
                    StaticResoureProcessor processor = new StaticResoureProcessor();
                    processor.process(request, response);
                }

                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
