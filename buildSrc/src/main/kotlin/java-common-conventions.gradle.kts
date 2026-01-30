plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}