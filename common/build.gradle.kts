plugins {
    id("java-common-conventions")
}

dependencies {
    // add common scoped dependencies here
    compileOnly(libs.bundles.common)
}
