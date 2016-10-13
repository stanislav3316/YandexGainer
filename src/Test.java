import Parsers.CookieParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by iters on 10/13/16.
 */
public class Test {
    private final String yandex_uid;
    private final String yandex_gid;
    private final String yp;
    private final String fuid01;
    private final String yabsFrequency;

    public Test() throws IOException {
        String request = "";
        String fuidRequest = "";
        String yabsRequest = "";
        try {
            request = getRespondViaHttpUrlConnection("https://www.yandex.ru/",
                    "./requests/getCookie", "Set-Cookie");
            fuidRequest = getRespondViaSockets("kiks.yandex.ru", "./requests/fuid01Request");
            yabsRequest = getRespondViaSockets("yabs.yandex.ru", "./requests/yabs-frequencyRequest");
        } catch (Exception e) {
            System.err.print("Error connection");
        }

        yandex_uid = CookieParser.getElementFromRespons(request, "yandexuid");
        yandex_gid = CookieParser.getElementFromRespons(request, "yandex_gid");
        yp = CookieParser.getElementFromRespons(request, "yp");
        fuid01 = CookieParser.getElementFromRespons(fuidRequest, "fuid01");
        yabsFrequency = CookieParser.getElementFromRespons(yabsRequest, "yabs-frequency");
    }

    public static void main(String[] args) throws Exception {
        new Test();
    }

    private String getRespondViaHttpUrlConnection(String url, String filePath, String field) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        String line;
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

        con.setRequestMethod("GET");
        while ((line = fileReader.readLine()) != null) {
            String header = line.substring(0, line.indexOf(":"));
            String value = line.substring(line.indexOf(":") + 1, line.length());
            con.setRequestProperty(header, value);
        }

        if ("".equalsIgnoreCase(field)) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : con.getHeaderFields().entrySet()) {
                builder.append(entry.getKey() + ":" + entry.getValue().toString() + "\n");
            }
            return builder.toString();
        } else {
            return con.getHeaderFields().get("Set-Cookie").toString();
        }
    }

    private String getRespondViaSockets(String url, String filePath) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(url), 80);
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        BufferedWriter sendTo = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        BufferedReader getMe = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line;

        while ((line = fileReader.readLine()) != null) {
            sendTo.write(line + "\r\n");
        }
        sendTo.write("\r\n");
        sendTo.flush();

        StringBuilder builder = new StringBuilder();
        while ((line = getMe.readLine()) != null && !("".equalsIgnoreCase(line))) {
            builder.append(line + "\n");
        }

        return builder.toString();
    }

}
