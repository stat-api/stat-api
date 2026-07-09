// Paginate by hand with from_id — NBA
// Generated from schema/api/examples/pagination-manual.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaPaginationManual {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Follow next_from_id until it is null
        Long fromId = null;
        int total = 0;
        int pageNum = 0;
        while (true) {
            var page = api.nba().teams().list(new NBATeamsListParams().limit(100L).fromId(fromId));
            pageNum++;
            total += page.rows().size();
            System.out.println("page " + pageNum + ": " + page.rows().size() + " rows, next cursor = " + page.nextFromId());
            if (page.nextFromId() == null) {
                break;
            }
            fromId = page.nextFromId();
        }
        System.out.println("walked " + total + " teams across " + pageNum + " pages by hand");

    }
}
