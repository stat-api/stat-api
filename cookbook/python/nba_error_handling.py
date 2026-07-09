# Handle errors by type — NBA
# Generated from schema/api/examples/error-handling.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Trigger a 404 and branch on the error
from statapi import NotFoundError, AuthenticationError, ValidationError, QuotaExceededError

try:
    api.nba.teams.get(999999999)
    print("unexpectedly found a team")
except NotFoundError as e:
    print(f"404 NotFoundError: no such team ({e.status})")
except AuthenticationError:
    print("401 AuthenticationError: bad or missing API key")
except ValidationError as e:
    print(f"400 ValidationError: {e.body}")
except QuotaExceededError:
    print("429 QuotaExceededError: monthly quota spent — never retried")
