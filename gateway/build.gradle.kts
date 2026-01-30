plugins {
    id("java-common-conventions")
}

dependencies {
    compileOnly(projects.common)
    modlImplementation(projects.web)

    compileOnly(libs.bundles.gateway)
    implementation(libs.bundles.gateway.perspective)
}
