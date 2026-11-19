// Rank a DFS slate by salary and value
// Generated from schema/api/examples/dfs-slate-to-salaries.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsSlateToSalaries {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Page the slate's scoring rows
        List<DFSPlayerStat> stats = new ArrayList<>();
        for (DFSPlayerStat row : api.dfs().playerStats().iterate(new DFSPlayerStatsListParams().slateId(129036L))) {
            stats.add(row);
        }

        // Drop the players who did not play
        List<DFSPlayerStat> scored = new ArrayList<>();
        for (DFSPlayerStat r : stats) {
            if (r.fantasyPts() != null) {
                scored.add(r);
            }
        }

        // Drop minimum-salary players
        List<DFSPlayerStat> playable = new ArrayList<>();
        for (DFSPlayerStat r : scored) {
            if (r.salary() >= 4000) {
                playable.add(r);
            }
        }

        // Compute points per $1,000 of salary
        record ValueRow(long playerId, String position, int salary, double points, double value) {}
        List<ValueRow> board = new ArrayList<>();
        for (DFSPlayerStat r : playable) {
            double pts = r.fantasyPts() == null ? 0.0 : r.fantasyPts();
            board.add(new ValueRow(r.playerId(), r.position(), r.salary(), pts, (pts / r.salary()) * 1000));
        }
        board.sort(Comparator.comparingDouble(ValueRow::value).reversed());
        board = board.subList(0, Math.min(10, board.size()));
        System.out.println(String.join("\t", "player_id", "position", "salary", "points", "per $1k"));
        for (ValueRow r : board) {
            System.out.printf("%d\t%s\t%d\t%.2f\t%.2f%n", r.playerId(), r.position(), r.salary(), r.points(), r.value());
        }

    }
}
