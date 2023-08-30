plugins {
    `kotlin-dsl`
    id("com.gevamu.build.gradle-plugin-conventions")
}

dependencies {
    implementation(project(":java-common-conventions"))
}

gradlePlugin {
    plugins {
        named("${project.group}.${project.name}") {
            displayName = "Gavamu Java library conventions plugin"
            description = "Gradle plugin defining common rules for Java libraries used in Gevamu builds"
        }
    }
}
