import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id(Plugins.Id.Android.Application) version Versions.Plugin.Gradle apply false
    id(Plugins.Id.Detekt) version Versions.Plugin.Detekt
    id(Plugins.Id.Ktlint) version Versions.Plugin.Ktlint
    id(Plugins.Id.Version) version Versions.Plugin.Version
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply {
        plugin(Plugins.Id.Detekt)
        plugin(Plugins.Id.Ktlint)
    }

    ktlint {
        debug.set(false)
        version.set(Versions.Ktlint)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String) = "^[0-9,.v-]+(-r)?$".toRegex().matches(version).not()
