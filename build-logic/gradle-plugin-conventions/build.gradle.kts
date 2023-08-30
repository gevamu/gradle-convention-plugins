plugins {
    `kotlin-dsl`
}

group = "com.gevamu.build"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(gradlePlugin("com.gradle.plugin-publish", "1.2.1"))
}

fun gradlePlugin(id: String, version: String) = "$id:$id.gradle.plugin:$version"
