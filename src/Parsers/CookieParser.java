package Parsers;

/**
 * Created by iters on 10/13/16.
 */
public class CookieParser {

    public static String getYandexuid(String request) {
        String res = "";
        res = request.substring(request.indexOf("yandexuid") + "yandexuid".length() + 1,
                request.indexOf(";"));
        return res;
    }

    public static String getYandex_gid(String request) {
        String res = "";
        int pos = request.indexOf("yandex_gid");
        res = request.substring(pos + "yandex_gid".length() + 1,
                request.indexOf(";", pos));
        return res;
    }

    public static String getYp(String request) {
        String res = "";
        int pos = request.indexOf("yp");
        res = request.substring(pos + "yp".length() + 1,
                request.indexOf(";", pos));
        return res;
    }

}
