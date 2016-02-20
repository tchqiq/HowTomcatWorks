package connector.http;

import org.apache.catalina.connector.http.SocketInputStream;
import org.apache.catalina.util.StringManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by heqi02 on 16/2/20.
 */
/* this class used to be called HttpServer */
public class HttpProcessor {

    private HttpRequest request;
    private HttpResponse response;

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    /**
     * The HttpConnector with which this processor is associated.
     */
    private HttpConnector connector = null;

    /**
     * The string manager for this package.
     */

    StringManager sm = StringManager.getManager("ex03.connector.http");

    public void process(Socket socket) {
        SocketInputStream input = null;
        OutputStream output = null;

        try {
            input = new SocketInputStream(socket.getInputStream(), 2048);
            output = socket.getOutputStream();
            // create HttpRequest object and parse
            request = new HttpRequest(input);

            // create HttpResponse object
            response = new HttpResponse(output);
            response.setRequest(request);

            response.setHeader("Server", "Pyrmont Servlet Container");

            parseRequest(input, output);
            parseHeaders(input);

            //check if this is a request for a servlet or a static resource
            //a request for a servlet begins with "/servlet/"
            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletProcess processor = new ServletProcess();
                processor.process(request, response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseHeaders(SocketInputStream input) {
    }

    private void parseRequest(SocketInputStream input, OutputStream output) {
    }
}
