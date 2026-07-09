// Hello, NHL
// Generated from schema/api/examples/hello-stat-api.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nhl.*;
import java.util.*;

public final class NhlHelloStatApi {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // List a few teams
        List<NHLTeam> teams = api.nhl().teams().list(new NHLTeamsListParams().limit(3L)).rows();

        // Print what came back
        System.out.println(teams);

    }
}
