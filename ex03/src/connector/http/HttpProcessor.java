package connector.http;

import org.apache.catalina.util.StringManager;

import javax.servlet.ServletException;
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

    /**
     * This method is the simplified version of the similar method in
     * org.apache.catalina.connector.http.HttpProcessor.
     * However, this method only parses some "easy" headers, such as
     * "cookie", "content-length", and "content-type", and ignore other headers.
     * @param input The input stream connected to our socket
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a parsing error occurs
     */
    private void parseHeaders(SocketInputStream input) {
        while (true) {
            HttpHeader header = new HttpHeader();
            input.readHeader(header);
        }
    }

    private void parseRequest(SocketInputStream input, OutputStream output) {
    }
}
