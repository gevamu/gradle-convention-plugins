plugins {
    `kotlin-dsl`
    id("com.gevamu.build.gradle-plugin-conventions")
}

dependencies {
    // Corda plugins
    implementation(gradlePlugin("net.corda.plugins.cordformation", "5.1.1"))
}

gradlePlugin {
    plugins {
        named("${project.group}.${project.name}") {
            displayName = "Gavamu Cordformation conventions plugin"
            description = "Gradle plugin defining rules for Cordformation used in Gevamu builds"
        }
    }
}

fun gradlePlugin(id: String, version: String) = "$id:$id.gradle.plugin:$version"
