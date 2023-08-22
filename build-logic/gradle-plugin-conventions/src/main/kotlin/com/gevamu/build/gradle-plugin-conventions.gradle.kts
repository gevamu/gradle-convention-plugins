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

val vcsUrl: String = "https://github.com/gevamu/gradle-conventions-plugins"

plugins {
    id("com.gradle.plugin-publish")
}

gradlePlugin {
    // XXX I'm not sure these settings affect anything
    website.set(vcsUrl)
    vcsUrl.set(vcsUrl)
}

publishing {
    repositories {
        maven {
            name = "gitHubPackages"
            setUrl(vcsUrl)
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

project.afterEvaluate {
    publishing.publications {
        named("pluginMaven", MavenPublication::class.java) {
            pom {
                scm {
                    if (!connection.isPresent && !developerConnection.isPresent && !url.isPresent) {
                        connection.set("scm:git:$vcsUrl.git")
                        developerConnection.set("scm:git:$vcsUrl.git")
                        url.set(vcsUrl)
                    }
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                organization {
                    if (!name.isPresent && !url.isPresent) {
                        name.set("Exactpro Systems Limited")
                        url.set("https://gevamu.com/")
                    }
                }
            }
        }
    }
}
