plugins {
    `kotlin-dsl`
    id("com.gevamu.build.gradle-plugin-conventions")
}

gradlePlugin {
    plugins {
        named("${project.group}.${project.name}") {
            displayName = "Gavamu CorDapp publishing conventions plugin"
            description = "Gradle plugin defining common rules for CorDapp publishing used in Gevamu builds"
        }
    }
}
