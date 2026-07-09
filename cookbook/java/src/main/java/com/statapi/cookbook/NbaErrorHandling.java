// Handle errors by type — NBA
// Generated from schema/api/examples/error-handling.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaErrorHandling {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Trigger a 404 and branch on the error
        try {
            api.nba().teams().get(999999999L);
            System.out.println("unexpectedly found a team");
        } catch (com.statapi.NotFoundException e) {
            System.out.println("404 NotFoundException: no such team (" + e.status() + ")");
        } catch (com.statapi.AuthenticationException e) {
            System.out.println("401 AuthenticationException: bad or missing API key");
        } catch (com.statapi.ValidationException e) {
            System.out.println("400 ValidationException: " + e.body());
        } catch (com.statapi.QuotaExceededException e) {
            System.out.println("429 QuotaExceededException: monthly quota spent — never retried");
        }

    }
}
