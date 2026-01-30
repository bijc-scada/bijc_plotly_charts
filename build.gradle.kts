import java.util.concurrent.TimeUnit
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    // the ignition module plugin: https://github.com/inductiveautomation/ignition-module-tools
    id("io.ia.sdk.modl") version("0.4.1")
    id("org.barfuin.gradle.taskinfo") version "3.0.0"
}

val sdk_version by extra("8.3.0")

allprojects {
    version = "2.83.0"
    group = "uk.co.bijc"
}

fun getDate(): String {
    var date = LocalDateTime.now()
    var formatter = DateTimeFormatter.ofPattern("yyyyMMddHH")
    return date.format(formatter)
}

fun getBuildDT(): String {
    var date = LocalDateTime.now()
    var formatter = DateTimeFormatter.ofPattern("yyMMddHHmm")
    return date.format(formatter)
}

fun getTime(): String {
    var date = LocalDateTime.now()
    var formatter = DateTimeFormatter.ofPattern("HHmm")
    return date.format(formatter)
}

ignitionModule {
    var company = "BIJC"
    var moduleName = "plotly"
    var licenseName = "license"

    // name of the .modl file to build
    fileName.set("${company}_Plotly_Component_v${project.version}.${getBuildDT()}")

    // module xml configuration
    name.set("BIJC Plotly Component")
    id.set("${project.group}.${moduleName}")
    moduleVersion.set("${project.version}.${getDate()}")
    moduleDescription.set("A component that implements the Plotly.js library as a component.")
    
    requiredIgnitionVersion.set("8.3.0")
    license.set("${licenseName}.html")

    moduleDependencies.put("com.inductiveautomation.perspective", "DG")

    projectScopes.putAll(
        mapOf(
            ":gateway" to "G",
            ":web" to "G",
            ":designer" to "D",
            ":common" to "GD"
        )
    )

    hooks.putAll(
        mapOf(
            "${project.group}.gateway.PlotlyGatewayHook" to "G",
            "${project.group}.designer.PlotlyDesignerHook" to "D"
        )
    )
    skipModlSigning.set(true)
}


val deepClean by tasks.registering {
    dependsOn(allprojects.map { "${it.path}:clean" })
    description = "Executes clean tasks and remove node plugin caches."
    doLast {
        delete(file(".gradle"))
    }
}
