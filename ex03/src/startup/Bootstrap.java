package startup;

import connector.http.HttpConnector;

/**
 * Created by heqi02 on 16/2/20.
 */
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}
