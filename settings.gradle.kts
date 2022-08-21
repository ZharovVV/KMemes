rootProject.name = "KMemes"
include(":app")

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.7.0") //такая версия из-за Compose
            // Версия Компилятора Compose 1.2.0 совместима c kotlin 1.7.0
            addAndroidCoreDependencies()
            addComposeDependencies()
            addComposeDebugDependencies()
            alias("coroutines").to("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
            val koinVersion = "3.2.0"
            addKoinDependencies(version = koinVersion)
            addKoinTestDependencies(version = koinVersion)
            addUnitTestsDependencies()
            addAndroidTestDependencies()
        }
    }
}

fun VersionCatalogBuilder.addAndroidCoreDependencies() {
    //android
    alias("androidx-core-ktx").to("androidx.core:core-ktx:1.8.0")
    alias("androidx-appcompat").to("androidx.appcompat:appcompat:1.4.2")
    alias("androidx-constraintlayout").to("androidx.constraintlayout:constraintlayout:2.1.3")
    alias("material").to("com.google.android.material:material:1.5.0")
    //for delegating ViewModel (by viewModels())
    alias("androidx-activity-ktx").to("androidx.activity:activity-ktx:1.5.0")
    bundle(
        "core",
        listOf(
            "androidx-core-ktx",
            "androidx-appcompat",
            "androidx-constraintlayout",
            "material",
            "androidx-activity-ktx"
        )
    )
}

fun VersionCatalogBuilder.addComposeDependencies() {
    //jetpack
    //compose
    version("compose", "1.2.0")
    alias("jetpack-compose-ui").to("androidx.compose.ui", "ui").versionRef("compose")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    alias("jetpack-compose-foundation")
        .to("androidx.compose.foundation", "foundation")
        .versionRef("compose")
    // Material Design
    alias("jetpack-compose-material")
        .to("androidx.compose.material", "material")
        .versionRef("compose")
    alias("jetpack-compose-material-icons-core")
        .to("androidx.compose.material", "material-icons-core")
        .versionRef("compose")
    alias("jetpack-compose-material-icons-extended")
        .to("androidx.compose.material", "material-icons-extended")
        .versionRef("compose")
    // compose material 3 (for dynamic color scheme)
    alias("jetpack-compose-material3").to("androidx.compose.material3:material3:1.0.0-alpha14")
    // Integration with activities
    alias("jetpack-compose-activity").to("androidx.activity:activity-compose:1.4.0")
    // Integration with ViewModels
    alias("jetpack-compose-viewmodel").to("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0")
    // Integration with observables
    alias("jetpack-compose-livedata").to("androidx.compose.runtime:runtime-livedata:1.1.1")
    //ViewPager for Compose
    alias("accompanist-pager").to("com.google.accompanist:accompanist-pager:0.24.11-rc")
    //Navigation Bar & Status Bar Color for Jetpack Compose
    alias("accompanist-system-ui-controller").to("com.google.accompanist:accompanist-systemuicontroller:0.25.0")
    //Navigation for Compose
    alias("navigation-compose").to("androidx.navigation:navigation-compose:2.5.0")

    bundle(
        "jetpack-compose",
        listOf(
            "jetpack-compose-ui",
            "jetpack-compose-foundation",
//            "jetpack-compose-material", не нужно так как используем Material 3
            "jetpack-compose-material-icons-core",
            "jetpack-compose-material-icons-extended",
            "jetpack-compose-material3",
            "jetpack-compose-activity",
            "jetpack-compose-viewmodel",
            "jetpack-compose-livedata",
            "accompanist-pager",
            "accompanist-system-ui-controller",
            "navigation-compose"
        )
    )
}

/**
 * Should use with debugImplementation(...)
 */
fun VersionCatalogBuilder.addComposeDebugDependencies() {
    // Tooling support (Previews, etc.)
    alias("jetpack-compose-ui-tooling")
        .to("androidx.compose.ui", "ui-tooling")
        .versionRef("compose")
    //region fix bug compose preview
    //https://stackoverflow.com/questions/71812710/can-no-longer-view-jetpack-compose-previews-failed-to-instantiate-one-or-more-c
    alias("jetpack-compose-customview")
        .to("androidx.customview:customview:1.2.0-alpha01")
    alias("jetpack-compose-customview-poolingcontainer")
        .to("androidx.customview:customview-poolingcontainer:1.0.0-rc01")
    //endregion
    bundle(
        "jetpack-compose-debug",
        listOf(
            "jetpack-compose-ui-tooling",
            "jetpack-compose-customview",
            "jetpack-compose-customview-poolingcontainer"
        )
    )
}

fun VersionCatalogBuilder.addUnitTestsDependencies() {
    //unit tests
    alias("junit").to("junit:junit:4.13.2")
    //maybe mockk
    bundle("test", listOf("junit"))
}

fun VersionCatalogBuilder.addAndroidTestDependencies() {
    //android tests
    alias("android-junit").to("androidx.test.ext:junit:1.1.3")
    alias("android-espresso").to("androidx.test.espresso:espresso-core:3.4.0")
    bundle("androidTest", listOf("android-junit", "android-espresso"))
    alias("androidTest-compose").to("androidx.compose.ui:ui-test-junit4:1.1.1")
}

fun VersionCatalogBuilder.addKoinDependencies(version: String) {
    alias("koin-core").to("io.insert-koin:koin-core:$version")
    alias("koin-android").to("io.insert-koin:koin-android:$version")
    alias("koin-compose").to("io.insert-koin:koin-androidx-compose:$version")
    bundle("koin", listOf("koin-core", "koin-android", "koin-compose"))
}

fun VersionCatalogBuilder.addKoinTestDependencies(version: String) {
    //для проверки графа из юнит-тестов (по другому проверить корректность графа не получится((()
    alias("koin-test").to("io.insert-koin:koin-test:$version")
}
