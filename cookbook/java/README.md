# Java cookbook

Maven Central and JitPack are deferred (JitPack needs a root build config for
the `java/` subdirectory module); build the Java SDK from source against these
examples (v0.3.1). Requires JDK 17+.

## Run (Gradle)

```sh
export STAT_API_KEY=...
gradle run -PmainClass=com.statapi.cookbook.NbaHelloStatApi
```

Every example is a `public static void main` class under
`com.statapi.cookbook`.

## NFL sim tutorial (beta)

The 5 recipes of the NFL sim tutorial are in this folder, for example `src/main/java/com/statapi/cookbook/NFLSimSummary.java`. Read [the tutorial](../nfl-sim/README.md) first.
