/*
 * Copyright 2023 Exactpro Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gevamu.build

plugins {
    id("com.gevamu.build.java-library-conventions")

    // Corda plugins
    id("net.corda.plugins.quasar-utils")
    id("net.corda.plugins.cordapp")
}

repositories {
    maven("https://software.r3.com/artifactory/corda")
    maven("https://repo.gradle.org/gradle/libs-releases-local")
    maven("https://jitpack.io")
}

dependencies {
    // Corda
    cordaProvided("net.corda:corda-core:4.9.3")
    cordaProvided("net.corda:corda-node-api:4.9.3")

    cordaRuntimeOnly("net.corda:corda:4.9.3")

    // logging. Corda 4.9 provides log4j 2.17.1
    cordaProvided("org.apache.logging.log4j:log4j-api:2.17.1")
    cordaProvided("org.apache.logging.log4j:log4j-core:2.17.1")

    testImplementation("net.corda:corda-node-driver:4.9.3")
}

tasks {
    test {
        javaLauncher.set(javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(8))
        })
    }
    jar {
        // This makes the JAR's SHA-256 hash repeatable.
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    named<Jar>("javadocJar") {
        from(named("dokkaJavadoc"))
    }
}

cordapp {
    targetPlatformVersion(8)
    minimumPlatformVersion(8)
}
