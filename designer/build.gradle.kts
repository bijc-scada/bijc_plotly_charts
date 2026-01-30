plugins {
    id("java-common-conventions")
}

dependencies {
    compileOnly(projects.common)
    
    compileOnly(libs.bundles.designer)
}
