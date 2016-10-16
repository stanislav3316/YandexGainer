import Search.YandexSearch;

import java.io.IOException;

/**
 * Created by iters on 10/16/16.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(new YandexSearch().getSearchResult("hello my name is Stanislav"));
    }
}
