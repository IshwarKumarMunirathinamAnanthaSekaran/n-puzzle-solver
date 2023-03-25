import java.util.List;

public class SearchResult {

    boolean found;
    List<String> actions;

    SearchResult(boolean found, List<String> actions) {
        this.found = found;
        this.actions = actions;
    }
}
