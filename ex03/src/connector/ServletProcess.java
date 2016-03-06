package connector;

import connector.http.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by heqi02 on 16/2/20.
 */
public class ServletProcess {
    public void process(HttpRequest request, HttpResponse response) {
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(Constants.WEB_ROOT);
        try {
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = URLClassLoader.newInstance(urls);
        } catch (IOException e) {
        }

        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        Servlet servlet = null;
        try {
            servlet = (Servlet) myClass.newInstance();
            HttpRequestFacade requestFacade = new HttpRequestFacade(request);
            HttpRespondeFacade respondeFacade = new HttpRespondeFacade(response);
            servlet.service(requestFacade, respondeFacade);
            response.finishResponse();
        } catch (InstantiationException e) {
            System.out.println(e.toString());
        } catch (IllegalAccessException e) {
            System.out.println(e.toString());
        } catch (ServletException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
