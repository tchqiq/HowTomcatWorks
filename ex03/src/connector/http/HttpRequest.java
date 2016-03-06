package connector.http;

import connector.RequestStream;
import org.apache.catalina.util.Enumerator;
import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by heqi02 on 16/2/20.
 */
public class HttpRequest implements HttpServletRequest {

    private String contentType;
    private int contentLength;
    private InetAddress inetAddress;
    private InputStream input;
    private String method;
    private String protocol;
    private String queryString;
    private String requestURI;
    private String smeerverName;
    private int serverPort;
    private Socket socket;
    private boolean requestedSessionCookie;
    private String requestedSessionId;
    private boolean requestedSessionURL;
    private String serverName;

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public boolean isRequestedSessionURL() {
        return requestedSessionURL;
    }

    public boolean isRequestedSessionCookie() {
        return requestedSessionCookie;
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setCookies(ArrayList cookies) {
        this.cookies = cookies;
    }

    public static void setEmpty(ArrayList empty) {
        HttpRequest.empty = empty;
    }

    public void setFormats(SimpleDateFormat[] formats) {
        this.formats = formats;
    }

    public void setHeaders(HashMap headers) {
        this.headers = headers;
    }

    public void setParameters(ParameterMap parameters) {
        this.parameters = parameters;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    /**
     * The request attributes for this request.
     */
    protected HashMap attributes = new HashMap();
    /**
     * The authorization credentials sent with this Request.
     */
    protected String authorization = null;
    /**
     * The context path for this request.
     */
    protected String contextPath = "";
    /**
     * The set of cookies associated with this Request.
     */
    protected ArrayList cookies = new ArrayList();
    /**
     * An empty collection to use for returning empty Enumerations.  Do not
     * add any elements to this collection!
     */
    protected static ArrayList empty = new ArrayList();
    /**
     * The set of SimpleDateFormat formats to use in getDateHeader().
     */
    protected SimpleDateFormat formats[] = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };

    /**
     * The HTTP headers associated with this Request, keyed by name.  The
     * values are ArrayLists of the corresponding header values.
     */
    protected HashMap headers = new HashMap();
    /**
     * The parsed parameters for this request.  This is populated only if
     * parameter information is requested via one of the
     * <code>getParameter()</code> family of method calls.  The key is the
     * parameter name, while the value is a String array of values for this
     * parameter.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong> - Once the parameters for a
     * particular request are parsed and stored here, they are not modified.
     * Therefore, application level access to the parameters need not be
     * synchronized.
     */
    protected ParameterMap parameters = null;

    protected String pathInfo = null;

    /**
     * The reader that has been returned by <code>getReader</code>, if any.
     */
    protected BufferedReader reader = null;

    /**
     * The ServletInputStream that has been returned by
     * <code>getInputStream()</code>, if any.
     */
    protected ServletInputStream stream = null;
    public HttpRequest(SocketInputStream input) {
        this.input = input;
    }


    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaders(String name) {
        name = name.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(name);
            if (values != null) {
                return (new Enumerator(values));
            } else {
                return new Enumerator(empty);
            }
        }
    }

    @Override
    public Enumeration getHeaderNames() {
        synchronized (headers) {
            return (new Enumerator(headers.keySet()));
        }
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String name) {
        parsePrameters();
        String values[] = (String[])parameters.get(name);
        if (values != null) {
            return values[0];
        } else {
            return null;
        }
    }

    @Override
    public Enumeration getParameterNames() {
        parsePrameters();
        return new Enumerator(parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        parsePrameters();
        String values[] = (String[])parameters.get(name);
        if (values != null) {
            return values;
        } else {
            return null;
        }
    }

    @Override
    public Map getParameterMap() {
        parsePrameters();
        return this.parameters;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (stream != null) {
            throw new IllegalStateException("getInputStream has been called.");
        }
        if (reader == null) {
            String encoding = getCharacterEncoding();
            if (encoding == null) {
                encoding = "ISO-8859-1";
            }
            InputStreamReader isr = new InputStreamReader(createInputStream(), encoding);
            reader = new BufferedReader(isr);
        }
        return reader;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    public void addHeader(String name, String value) {
        name = name.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(name);
            if (values == null) {
                values = new ArrayList();
                headers.put(name, values);
            }
            values.add(value);
        }
    }

    /**
     * Have the parameters for this request been parsed yet?
     */
    protected boolean parsed = false;

    /**
     * Parse the parameters of this request, if it has not already occurred.
     * If parameters are present in both the query string and the request
     * content, they are merged.
     */
    protected void parsePrameters() {
        if (parsed) {
            return;
        }
        ParameterMap results = parameters;
        if (results == null) {
            results = new ParameterMap();
        }
        results.setLocked(false);
        String encoding = getCharacterEncoding();
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }

        // Parse any parameters specified in the query string
        String queryString = getQueryString();

        try {
            RequestUtil.parseParameters(results, queryString, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Parse any parameters specified in the input stream
        String contentType = getContentType();
        if (contentType == null) {
            contentType = "";
        }
        int semicolon = contentType.indexOf(";");
        if (semicolon >= 0) {
            contentType = contentType.substring(0, semicolon).trim();
        }
        else {
            contentType = contentType.trim();
        }
        if ("POST".equals(getMethod()) && (getContentLength() > 0) && "application/x-www-form-urlencoded".equals(contentType)) {
            try {
                int max = getContentLength();
                int len = 0;
                byte buf[] = new byte[getContentLength()];
                ServletInputStream is = getInputStream();
                while (len < max) {
                    int next = is.read(buf, len, max - len);
                    if (next < 0) {
                        break;
                    }
                    len += next;
                }
                is.close();
                if (len < max) {
                    throw new RuntimeException("Content length mismatch");
                }
                RequestUtil.parseParameters(results, buf, encoding);
            } catch (IOException e) {
                throw new RuntimeException("Content read fail!");
            }
        }

        // Store the final results
        results.setLocked(true);
        parsed = true;
        parameters = results;
    }

    public void addCookie(Cookie cookie) {
        synchronized (cookies) {
            cookies.add(cookie);
        }
    }

    /**
     * Create and return a ServletInputStream to read the content
     * associated with this Request.  The default implementation creates an
     * instance of RequestStream associated with this request, but this can
     * be overridden if necessary.
     *
     * @exception IOException if an input/output error occurs
     */
    public ServletInputStream createInputStream() throws IOException {
        return (new RequestStream(this));
    }

    public InputStream getStream() {
        return input;
    }
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestedSessionId(String substring) {
        this.requestedSessionId = substring;
    }

    public void setRequestedSessionURL(boolean flag) {
        requestedSessionURL = flag;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setRequestedSessionCookie(boolean flag) {
        this.requestedSessionCookie = flag;
    }

    public void setContentLength(int length) {
        this.contentLength = length;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
}
