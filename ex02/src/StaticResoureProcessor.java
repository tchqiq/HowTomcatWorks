import java.io.IOException;

/**
 * Created by heqi02 on 16/2/18.
 */
public class StaticResoureProcessor {
    public void process(Request request, Response response) {
        try {
            response.sendStaticResouce();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
