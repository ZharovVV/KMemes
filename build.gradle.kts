buildscript {
    repositories {
        google()
        mavenCentral()
        //for se.ascp.gradle:gradle-versions-filter:0.1.16
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
        classpath("se.ascp.gradle:gradle-versions-filter:0.1.16")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

/**
 * TODO удалить, когда для versionCatalog заработают предложения обновления версий
 * [bug](https://issuetracker.google.com/issues/226078451)
 *
 * См. [описание работы](https://github.com/janderssonse/gradle-versions-filter-plugin)
 * Для запуска плагина нужно выполнить задачу:
 * ```
 * ./gradlew dependencyUpdates
 * ```
 */
apply(plugin = "se.ascp.gradle.gradle-versions-filter")

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}