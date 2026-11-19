plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Composite build (settings.gradle.kts) substitutes ../../java automatically.
    implementation("com.stat-api:statapi:0.3.0")
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
}
