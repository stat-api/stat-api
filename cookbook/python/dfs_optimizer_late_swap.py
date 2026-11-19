# Late-swap unlocked slots on active contest entries — DFS
# Generated from schema/api/examples/dfs-optimizer-late-swap.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Run fast late swap with locked game 43545
swapRes = api.dfs.slates.optimizer_lineups(1126128, {"entries": [{"entry_id": "entry-1", "slate_player_ids": [44186595, 44186556, 44186645, 44186641, 44186568, 44186860, 44186666, 44186564, 44187045]}], "as_of": "2025-01-01T00:00:00Z", "locked_game_ids": [43545], "min_projection_gain": 0})

# Print per-entry swap decision and gain
swapped_cnt = swapRes["portfolio"].get("swapped_count") or 0
held_cnt = swapRes["portfolio"].get("held_count") or 0
print(f"Slate {swapRes['slate']['id']} | entries={len(swapRes['lineups'])} swapped={swapped_cnt} held={held_cnt}")
def _nz(v: float) -> float: return 0.0 if abs(v) < 0.005 else v
for l in swapRes["lineups"]:
    hold_pts = l.get("hold_projected_points") if l.get("hold_projected_points") is not None else l["projected_points"]
    gain_pts = _nz(l.get("projection_gain") or 0.0)
    locked_cnt = len(l.get("locked_slots") or [])
    print(f"Entry {l.get('entry_id') or 'entry'} | action={l.get('action') or ''} salary={l['salary']} hold={hold_pts:.2f} proj={l['projected_points']:.2f} gain={gain_pts:.2f} locked_slots={locked_cnt}")
