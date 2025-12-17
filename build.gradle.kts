import java.util.concurrent.TimeUnit
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime


plugins {
    base
    // the ignition module plugin: https://github.com/inductiveautomation/ignition-module-tools
    id("io.ia.sdk.modl") version("0.4.1")
    id("org.barfuin.gradle.taskinfo") version "3.0.0"
}

allprojects {
    version = "2.2.0"
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

    // If we depend on other module being loaded/available, then we specify IDs of the module we depend on,
    // and specify the Ignition Scope that applies. "G" for gateway, "D" for designer, "C" for VISION client
    // (this module does not run in the scope of a Vision client, so we don't need a "C" entry here)
    moduleDependencies.put("com.inductiveautomation.perspective", "DG")

    // map of 'Gradle Project Path' to Ignition Scope in which the project is relevant.  This is is combined with
    // the dependency declarations within the subproject's build.gradle.kts in order to determine which
    // dependencies need to be bundled with the module and added to the module.xml.
    projectScopes.putAll(
        mapOf(
            ":gateway" to "G",
            ":web" to "G",
            ":designer" to "D",
            ":common" to "GD"
        )
    )

    // 'hook classes' are the things that Ignition loads and runs when your module is installed.  This map tells
    // Ignition which classes should be loaded in a given scope.
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
