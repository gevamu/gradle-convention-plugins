val kotlinVersion: String by project

plugins {
    id("com.gevamu.build.gradle-plugin-conventions")
}

dependencies {
    implementation(gradlePlugin("org.jetbrains.kotlin.jvm", kotlinVersion))
    // Kotlin style checker (3.0.x is for Kotlin 1.4)
    implementation(gradlePlugin("org.jmailen.kotlinter", "3.0.2"))
    // Documentation engine for Kotlin
    implementation(gradlePlugin("org.jetbrains.dokka", kotlinVersion))
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substituteKotlinModule("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
        substituteKotlinModule("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }
}

gradlePlugin {
    plugins {
        create("${project.group}.${project.name}")  {
            id = "${project.group}.${project.name}"
            implementationClass = "${group}.KotlinCommonPlugin"
            displayName = "Gavamu Kotlin common conventions plugin"
            description = "Gradle plugin defining rules for Kotlin projects used in Gevamu builds"
        }
    }
}

fun gradlePlugin(id: String, version: String) = "$id:$id.gradle.plugin:$version"

fun DependencySubstitutions.substituteKotlinModule(moduleNotation: String) {
    substitute(module(moduleNotation)).using(module("$moduleNotation:$kotlinVersion"))
}
