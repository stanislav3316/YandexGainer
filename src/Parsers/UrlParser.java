package Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iters on 10/17/16.
 */
public class UrlParser {
    public String getLink(String html, int num) {
        String result = "";
        Pattern p = Pattern.compile("<div class=\"a11y-hidden\">" + num + "</div>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            int pos = m.start();
            String block = html.substring(pos, );
        }

        return result;
    }

}
