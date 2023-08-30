plugins {
    `kotlin-dsl`
    id("com.gevamu.build.gradle-plugin-conventions")
}

gradlePlugin {
    plugins {
        named("${project.group}.${project.name}") {
            displayName = "Gavamu Java common conventions plugin"
            description = "Gradle plugin defining rules for Java projects used in Gevamu builds"
        }
    }
}
