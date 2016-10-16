package Init;

import Const.Const;
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
public class InitCookie {
    public String yandex_uid;
    public String yandex_gid;
    public String yp;
    public String fuid01;
    public String yabsFrequency;

    public InitCookie() throws IOException {
        String request;
        String fuidRequest;
        String yabsRequest;
        try {
            request = getRespondViaHttpUrlConnection("https://www.yandex.ru/",
                    "./requests/getCookie", "Set-Cookie");
            yandex_uid = CookieParser.getElementFromRespons(request, "yandexuid");
            yandex_gid = CookieParser.getElementFromRespons(request, "yandex_gid");
            yp = CookieParser.getElementFromRespons(request, "yp");

            addCookietoFile("./requests/fuid01Request",
                    "yp=" + yp, "yandex_gid=" + yandex_gid, "yandexuid=" + yandex_uid,
                    "_ym_uid=" + Const._YM_UID, "zm=" + Const.ZM);

            fuidRequest = getRespondViaSockets("kiks.yandex.ru", "./requests/fuid01Request");

            addCookietoFile("./requests/yabs-frequencyRequest",
                    "yp=" + yp, "yandex_gid=" + yandex_gid, "yandexuid=" + yandex_uid,
                    "_ym_uid=" + Const._YM_UID, "zm=" + Const.ZM);
            yabsRequest = getRespondViaSockets("yabs.yandex.ru", "./requests/yabs-frequencyRequest");

            fuid01 = CookieParser.getElementFromRespons(fuidRequest, "fuid01");
            yabsFrequency = CookieParser.getElementFromRespons(yabsRequest, "yabs-frequency");
            // необходимо следить за Location!

            removeCookieLastLine("./requests/fuid01Request");
            removeCookieLastLine("./requests/yabs-frequencyRequest");
        } catch (Exception e) {
            System.err.print("Error connection");
        }
        System.out.println(yandex_uid);
        System.out.println(yandex_gid);
        System.out.println(yp);
        System.out.println(fuid01);
        System.out.println(yabsFrequency);
        System.out.println();
    }

    public void addCookietoFile(String file, String... args) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("Cookie: ");
        for (String s : args) {
            builder.append(s + "; ");
        }

        try {
            BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(file,true));
            bufferWritter.write("\n" + builder.toString());
            bufferWritter.close();
        } catch(IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public void removeCookieLastLine(String file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder builder = new StringBuilder();

        while((line = bf.readLine()) != null) {
            if (!line.startsWith("Cookie")) {
                builder.append(line + "\n");
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(builder.toString());
        bw.close();
    }

    public String getRespondViaHttpUrlConnection(String url, String filePath) {
        try {
            return getRespondViaHttpUrlConnectionInner(url, filePath, "");
        } catch (Exception e) {
            throw new RuntimeException("getRespondViaHttpUrlConnection Error withouw field");
        }
    }

    public String getRespondViaHttpUrlConnection(String url, String filePath, String field) {
        try {
            return getRespondViaHttpUrlConnectionInner(url, filePath, field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getRespondViaHttpUrlConnection Error with fiels");
        }
    }

    private String getRespondViaHttpUrlConnectionInner(String url, String filePath, String field) throws Exception {
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

    public  String getRespondViaSockets(String url, String filePath) throws IOException {
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
