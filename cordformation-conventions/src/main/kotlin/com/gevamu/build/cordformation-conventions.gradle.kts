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
    id("net.corda.plugins.cordformation")
}

// XXX or by project() ?
val corda_release_version: String by extra("4.9.3")

repositories {
    mavenCentral()
    maven("https://software.r3.com/artifactory/corda")
    maven("https://repo.gradle.org/gradle/libs-releases-local")
    maven("https://jitpack.io")
}

dependencies {
    corda("net.corda:corda:4.9.3")
    cordaBootstrapper("net.corda:corda-node-api:4.9.3")
}

configurations.all {
    resolutionStrategy {
        dependencySubstitution {
            // caffein has 2 variants: runtimeElements and shadowRuntimeElements.
            // We need to help gradle choosing one of them
            substitute(
                module("com.github.ben-manes.caffeine:caffeine:2.9.3")
            ).using(
                variant(module("com.github.ben-manes.caffeine:caffeine:2.9.3")) {
                    attributes {
                        // Chose runtimeElements (external dependencies)
                        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
                    }
                }
            )
        }
    }
}
