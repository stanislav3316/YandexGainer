package handler;

/**
 * Created by iters on 10/21/16.
 */
public class SearchNode {
    public String link;
    public String title;

    public SearchNode(String link, String title) {
        this.title = title;
        this.link = link;
    }

    @Override
    public String toString() {
        return link + "\n" + title;
    }
}
