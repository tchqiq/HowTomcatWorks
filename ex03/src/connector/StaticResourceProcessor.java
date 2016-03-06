package connector;

import connector.http.HttpRequest;
import connector.http.HttpResponse;

import java.io.IOException;

/**
 * Created by heqi02 on 16/2/23.
 */
public class StaticResourceProcessor {
    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
