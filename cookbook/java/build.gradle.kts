plugins {
    application
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Maven Central + JitPack are deferred (JitPack needs a root build
    // config for the java/ subdirectory module); build from source for now.
    implementation("com.github.stat-api:stat-api:v0.1.0")
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
}
