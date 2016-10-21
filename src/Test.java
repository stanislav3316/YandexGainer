import Parsers.UrlParser;
import Search.YandexSearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Created by iters on 10/16/16.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        UrlParser parser = new UrlParser();
        String search = new YandexSearch().getSearchResult("привет");
        PrintWriter pw = new PrintWriter("out.txt");
        for (int i = 1; i < 10; i++) {
            pw.write(String.valueOf(parser.getLink(search, i)) + "\n\n");
        }
        pw.close();
    }
}
