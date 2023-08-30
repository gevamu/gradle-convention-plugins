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
    `maven-publish`
    signing
}

enum class LicenseType {
    APACHE_2,
    EXACTPRO
}

interface CordappPublishingExtension {
    val name: Property<String>
    val description: Property<String>
    val vcsUrl: Property<String>
    // TODO Get from cordapp.contract
    val license: Property<LicenseType>
}

val extension: CordappPublishingExtension = extensions.create<CordappPublishingExtension>("cordappPublishing")

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(project.components["cordapp"])
            artifact(project.tasks["javadocJar"])
            artifact(project.tasks["sourcesJar"])
        }
    }
    repositories {
        maven {
            name = "mavenCentral"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications[project.name])
}

project.afterEvaluate {
    publishing {
        publications {
            named<MavenPublication>(project.name) {
                pom {
                    if (extension.name.isPresent && !name.isPresent) {
                        name.set(extension.name.get())
                    }
                    if (extension.description.isPresent && !description.isPresent) {
                        description.set(extension.description.get())
                    }
                    if (extension.vcsUrl.isPresent && !url.isPresent) {
                        url.set(extension.vcsUrl.get())
                    }
                    if (extension.license.isPresent) {
                        licenses {
                            when (extension.license.get()) {
                                LicenseType.APACHE_2 ->
                                    license {
                                        name.set("The Apache License, Version 2.0")
                                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                                    }
                                LicenseType.EXACTPRO ->
                                    license {
                                        name.set("Exactpro Commercial License")
                                    }
                            }
                        }
                    }
                    if (extension.vcsUrl.isPresent) {
                        val vcsUrl: String = extension.vcsUrl.get()
                        scm {
                            if (!connection.isPresent) {
                                connection.set("scm:git:$vcsUrl.git")
                            }
                            if (!developerConnection.isPresent) {
                                developerConnection.set("scm:git:$vcsUrl.git")
                            }
                            if (!url.isPresent) {
                                url.set(vcsUrl)
                            }
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
}
