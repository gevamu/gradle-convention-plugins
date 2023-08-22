plugins {
    `kotlin-dsl`
    id("com.gevamu.build.gradle-plugin-conventions")
}

dependencies {
    implementation(project(":java-library-conventions"))
    // Corda plugins
    implementation(gradlePlugin("net.corda.plugins.quasar-utils", "5.1.1"))
    implementation(gradlePlugin("net.corda.plugins.cordapp", "5.1.1"))
}

gradlePlugin {
    plugins {
        named("${project.group}.${project.name}") {
            displayName = "Gavamu CorDapp conventions plugin"
            description = "Gradle plugin defining common rules for CorDapps used in Gevamu builds"
        }
    }
}

fun gradlePlugin(id: String, version: String) = "$id:$id.gradle.plugin:$version"
