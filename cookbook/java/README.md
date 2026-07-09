# Java cookbook

Maven Central and JitPack are deferred (JitPack needs a root build config for
the `java/` subdirectory module); build the Java SDK from source against these
examples (v0.1.0). Requires JDK 17+.

## Run (Gradle)

```sh
export STAT_API_KEY=...
gradle run -PmainClass=com.statapi.cookbook.NbaHelloStatApi
```

Every example is a `public static void main` class under
`com.statapi.cookbook`.
