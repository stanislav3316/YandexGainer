package Parsers;

/**
 * Created by iters on 10/13/16.
 */
public class CookieParser {
    public static String getElementFromRespons(String request, String element) {
        String res = "";
        int pos = request.indexOf(element);
        res = request.substring(pos + element.length() + 1,
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
