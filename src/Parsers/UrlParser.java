package Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iters on 10/17/16.
 */
public class UrlParser {
    public SearchNode getLink(String html, int num) {
        String result = "";
        String link = "";
        String find = "<div class=\"a11y-hidden\">" + num + "</div>";

        Pattern p = Pattern.compile(find);
        Matcher m = p.matcher(html);

        if (m.find()) {
            int pos = m.start();
            String block = html.substring(pos + find.length(), html.length());
            result = block.substring(block.indexOf("</span>"), block.indexOf("</a>"));
        }

        find = "<a class=\"link link_minor_yes sitelinks__link\" target=\"_blank\"";
        m.reset(html);

        if (m.find()) {
            int pos = m.start();
            String block = html.substring(pos + find.length(), html.length());
            link = block.substring(block.indexOf("href"), block.indexOf("onmousedown") );
        }

        SearchNode node = new SearchNode(link, result);
        node = urlHandler(node);
        return node;
    }

    private SearchNode urlHandler(SearchNode node) {
        String link = node.link;
        link = link.substring(link.indexOf("\"") + 1, link.length() - 2);

        String title = node.title;
        title = title.substring(title.indexOf("</span>") + "</span>".length(), title.length());
        title = title.replaceAll("</b>", "");
        title = title.replaceAll("\\.\\.\\.", "");
        title = title.replaceAll("<b class=\"needsclick\">", "");

        node.title = title;
        node.link = link;
        return node;
    }

    class SearchNode {
        String link;
        String title;

        public SearchNode(String link, String title) {
            this.title = title;
            this.link = link;
        }

        @Override
        public String toString() {
            return link + "\n" + title;
        }
    }
}
