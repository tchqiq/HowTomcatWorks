import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by heqi02 on 16/2/18.
 */
public class ServletProcessor1 {
    public void process(Request request, Response response) {

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf('/') + 1);
        URLClassLoader loader = null;

        // create a URLClassLoader
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(Constants.WEB_ROOT);
        // the forming of repository is taken from the createClassLoader method in
        // org.apache.catalina.startup.ClassLoaderFactory
        try {
//            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = URLClassLoader.newInstance(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
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
            servlet.service((ServletRequest) request, (ServletResponse) response);
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
