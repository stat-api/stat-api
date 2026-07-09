// Read your quota off a response — NBA
// Generated from schema/api/examples/quota-headers.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaQuotaHeaders {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // List a page and read its quota
        var page = api.nba().teams().list(new NBATeamsListParams().limit(3L));
        if (page.quota() != null) {
            System.out.println("quota: " + page.quota().remaining() + " of " + page.quota().limit() + " records left this month");
        } else {
            System.out.println("no quota headers on this response");
        }

    }
}
