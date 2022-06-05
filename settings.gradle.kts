rootProject.name = "KMemes"
include(":app")

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.6.10")

            //android
            alias("androidx-core-ktx").to("androidx.core:core-ktx:1.7.0")
            alias("androidx-appcompat").to("androidx.appcompat:appcompat:1.4.1")
            alias("androidx-constraintlayout").to("androidx.constraintlayout:constraintlayout:2.1.3")
            alias("material").to("com.google.android.material:material:1.5.0")
            //for delegating ViewModel (by viewModels())
            alias("androidx-activity-ktx").to("androidx.activity:activity-ktx:1.4.0")
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
            //jetpack
            //compose
            version("compose", "1.1.1")
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
            // Integration with activities
            alias("jetpack-compose-activity").to("androidx.activity:activity-compose:1.4.0")
            // Integration with ViewModels
            alias("jetpack-compose-viewmodel").to("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
            // Integration with observables
            alias("jetpack-compose-livedata").to("androidx.compose.runtime:runtime-livedata:1.1.1")
            bundle(
                "jetpack-compose",
                listOf(
                    "jetpack-compose-ui",
                    "jetpack-compose-foundation",
                    "jetpack-compose-material",
                    "jetpack-compose-material-icons-core",
                    "jetpack-compose-material-icons-extended",
                    "jetpack-compose-activity",
                    "jetpack-compose-viewmodel",
                    "jetpack-compose-livedata"
                )
            )
            // Tooling support (Previews, etc.)
            alias("jetpack-compose-ui-tooling")
                .to("androidx.compose.ui", "ui-tooling")
                .versionRef("compose")
            bundle("jetpack-compose-debug", listOf("jetpack-compose-ui-tooling"))

            //unit tests
            alias("junit").to("junit:junit:4.13.2")
            //maybe mockk
            bundle("test", listOf("junit"))

            //android tests
            alias("android-junit").to("androidx.test.ext:junit:1.1.3")
            alias("android-espresso").to("androidx.test.espresso:espresso-core:3.4.0")
            bundle("androidTest", listOf("android-junit", "android-espresso"))
            alias("androidTest-compose").to("androidx.compose.ui:ui-test-junit4:1.1.1")
        }
    }
}
