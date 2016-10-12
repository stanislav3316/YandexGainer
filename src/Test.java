import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by iters on 10/13/16.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        new Test().sendGet();
    }

    private void sendGet() throws Exception {
        String url = "https://yandex.ru/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        String line;
        BufferedReader fileReader = new BufferedReader(new FileReader("./requests/getCookie"));

        con.setRequestMethod("GET");
        while ((line = fileReader.readLine()) != null) {
            String header = line.substring(0, line.indexOf(":"));
            String value = line.substring(line.indexOf(":") + 1, line.length());
            con.setRequestProperty(header, value);
        }

        int responseCode = con.getResponseCode();
        System.out.println("request code = " + responseCode);

        Map<String, List<String>> responseHeader =  con.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : responseHeader.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
