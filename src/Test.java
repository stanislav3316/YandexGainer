import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by iters on 10/13/16.
 */
public class Test {
    public static void main(String[] args) {
        
    }

    private void sendGet() throws Exception {
        String url = "https://www.yandex.ru/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        con.setRequestProperty("Accept-Encoding", "UTF-8");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Cache-Control", "max-age=0");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Host", "www.yandex.ru");
        con.setRequestProperty("Referer", "https://yandex.ru/search/?text=%D0%BA%D0%B0%D0%BA%20%D0%BE%D0%B1%D0%BE%D0%B9%D1%82%D0%B8%20%D0%BA%D0%B0%D0%BF%D1%87%D1%83%20%D1%8F%D0%BD%D0%B4%D0%B5%D0%BA%D1%81%D0%B0&lr=2");
        con.setRequestProperty("Upgrade-Insecure-Requests", "1");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println(con.getResponseMessage());
        System.out.println(con.getHeaderField(0));
        System.out.println(con.getHeaderField(1));
        System.out.println(con.getHeaderField(2));
        System.out.println(con.getHeaderField(3));
        System.out.println(con.getHeaderField(4));
        System.out.println(con.getHeaderField(5));
        System.out.println(con.getHeaderField(6));
        System.out.println(con.getHeaderField(7));
        System.out.println(con.getHeaderField(8));
        System.out.println(con.getHeaderField(9));
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();
        PrintWriter pw = new PrintWriter(new FileWriter("index.txt"));

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            pw.write(inputLine);
        }
        in.close();
        pw.close();
        //print result
        //System.out.println(response.toString());
        //System.out.println(response.toString());
        //System.out.println(response.toString());

    }
}
