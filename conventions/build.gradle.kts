plugins {
    `kotlin-dsl`
    `maven-publish`
}
group = "computer.kolektiv.gradle"
version = rootProject.version
repositories {
    mavenCentral()
    gradlePluginPortal()
}
gradlePlugin {
    plugins {
        create("publishing") {
            id = "computer.kolektiv.publishing"
            implementationClass = "computer.kolektiv.gradle.PublishingConventionPlugin"
        }
    }
}
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/KolektivComputer/gradle-conventions")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
