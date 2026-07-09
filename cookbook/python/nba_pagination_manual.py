# Paginate by hand with from_id — NBA
# Generated from schema/api/examples/pagination-manual.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Follow next_from_id until it is null
from_id = None
total = 0
page_num = 0
while True:
    page = api.nba.teams.list(limit=100, from_id=from_id)
    page_num += 1
    total += len(page.rows)
    print(f"page {page_num}: {len(page.rows)} rows, next cursor = {page.next_from_id}")
    if page.next_from_id is None:
        break
    from_id = page.next_from_id
print(f"walked {total} teams across {page_num} pages by hand")
