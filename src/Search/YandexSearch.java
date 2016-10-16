package Search;

import Const.Const;
import Init.InitCookie;

import java.io.*;
import java.net.*;

/**
 * Created by iters on 10/14/16.
 */
public class YandexSearch {
    public InitCookie cookies;

    public YandexSearch() {
        try {
            cookies = new InitCookie();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // получить справку!!!!
    public String getSearchResult(String params) throws IOException {
        URL obj = new URL("https://yandex.ru/search/?lr=2&msid=" +
                "1476477323.09596.22878.21893&" +
                "text=" + URLEncoder.encode(params, "UTF-8") + "&" +
                "suggest_reqid=414290922147414471573315667292099&" +
                "csg=333%2C2800%2C14%2C10%2C0%2C0%2C0");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        String line;
        BufferedReader fileReader = new BufferedReader(new FileReader("./requests/respondForSearching"));

        con.setRequestMethod("GET");
        while ((line = fileReader.readLine()) != null) {
            String header = line.substring(0, line.indexOf(":"));
            String value = line.substring(line.indexOf(":") + 1, line.length());
            con.setRequestProperty(header, value);
        }
        con.setRequestProperty("Cookie", initCookies());

        StringBuilder builder = new StringBuilder();
        BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        while((line = bf.readLine()) != null) {
            builder.append(line + "\n");
        }

        return builder.toString();
    }
    private String initCookies() throws IOException {
        return gainGookie("./requests/respondForSearching",
                        "z=s:l:0bdb8a715b:1476476380412",
                        "b=srunQXi2J9j7a%40%3F%7D3p0%2BIgs%3E%7B%3FWkR%3CaQIWZ9i8Tkr%3FKH1%25x%3D%2BaND%26m%3FlewQX9yd7OAm6o6tEf*eLuAD49lBKPw%23z2k%3AGBiv%5D%2BJ%3A9%3AaWM3*E%5BTZRBc",
                        "yp=" + cookies.yp, "yandex_gid=" + cookies.yandex_gid,
                        "yandexuid=" + cookies.yandex_uid,
                        "_ym_uid=" + Const._YM_UID, "zm=" + Const.ZM,
                        "fuid01=" + cookies.fuid01,
                        "yabs-frequency=" + cookies.yabsFrequency,
                        "_ym_isad=" + Const._YM_ISAD,
                        "ys=wprid.1476477285634541-18286793526058055825150137-sfront6-002",
                        "spravka=dD0xNDc2NDc2NzMyO2k9ODkuMjIzLjQ3LjIxOTt1PTE0NzY0NzY3MzI4ODE3OTc4MjA7aD03MTJlNGM5MDEyYmY4NWM5NGFmOTEwNDYwOTc5OThiNQ==",
                        "i=k/LK8uS31tVmNMmGnrvvUWAIeVU+kdnQ1+EWkGm/beGHOuq5WE7qeDgbW/XEe3bZ6exVDFhwUqUG6Qz7HbwMoCk5Nw==", "mda=0");
    }

    public String gainGookie(String file, String... str) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String s : str) {
            builder.append(s + "; ");
        }
        return builder.toString();
    }
}
