package computer.kolektiv.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

/** Org publishing: GitHub Packages + optional Yuri Capital. */
class PublishingConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.withPlugin("maven-publish") {
            project.extensions.configure<PublishingExtension> {
                publications.withType<MavenPublication>().configureEach {
                    pom {
                        url.set(
                            project.providers.gradleProperty("kolektiv.scmUrl")
                                .orElse("https://github.com/KolektivComputer/" + project.rootProject.name)
                        )
                        licenses {
                            license {
                                name.set(project.providers.gradleProperty("kolektiv.licenseName").orElse("Apache-2.0"))
                                url.set(
                                    project.providers.gradleProperty("kolektiv.licenseUrl")
                                        .orElse("https://www.apache.org/licenses/LICENSE-2.0.txt")
                                )
                            }
                        }
                        developers {
                            developer {
                                id.set("kolektiv")
                                name.set("Kolektiv")
                                organization.set("Kolektiv Computer")
                            }
                        }
                    }
                }
                repositories {
                    val actor = System.getenv("GITHUB_ACTOR")
                    val token = System.getenv("GITHUB_TOKEN")
                    if (!actor.isNullOrBlank() && !token.isNullOrBlank()) {
                        maven {
                            name = "GitHubPackages"
                            url = uri("https://maven.pkg.github.com/KolektivComputer/" + project.rootProject.name)
                            credentials {
                                username = actor
                                password = token
                            }
                        }
                    }
                    val yuriUser = project.providers.environmentVariable("YURI_CAPITAL_REPO_USERNAME")
                    val yuriPass = project.providers.environmentVariable("YURI_CAPITAL_REPO_PASSWORD")
                    if (yuriUser.isPresent && yuriPass.isPresent) {
                        val snapshot = project.version.toString().endsWith("-SNAPSHOT", ignoreCase = true)
                        maven {
                            name = if (snapshot) "yuriSnapshots" else "yuriReleases"
                            url = uri(
                                if (snapshot) "https://repo.yuri.capital/repository/maven-snapshots/"
                                else "https://repo.yuri.capital/repository/maven-releases/"
                            )
                            credentials {
                                username = yuriUser.get()
                                password = yuriPass.get()
                            }
                        }
                    }
                }
            }
        }
    }
}
