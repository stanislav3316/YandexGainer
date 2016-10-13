import Parsers.CookieParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by iters on 10/13/16.
 */
public class Test {
    private final String yandex_uid;
    private final String yandex_gid;
    private final String yp;

    public Test() {
        String request = "";
        try {
            request = sendFirstGet();
        } catch (Exception e) {
            System.err.print("Error connection");
        }
        
        yandex_uid = CookieParser.getYandexuid(request);
        yandex_gid = CookieParser.getYandex_gid(request);
        yp = CookieParser.getYp(request);
    }

    public static void main(String[] args) throws Exception {
        new Test();
    }

    // get yandexuid, yandex_gid, yp
    private String sendFirstGet() throws Exception {
        String url = "https://www.yandex.ru/";
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

        return con.getHeaderFields().get("Set-Cookie").toString();
    }

    // get fuid01
    private String getFuid01Request() throws IOException {
        String url = "https://www.yandex.ru/";
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

        return con.getHeaderFields().get("Set-Cookie").toString();
    }
}
