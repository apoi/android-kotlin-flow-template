object Sdk {
    const val MinSdkVersion = 23
    const val TargetSdkVersion = 29
    const val CompileSdkVersion = 29
}

object Plugins {
    object Id {
        object Kotlin {
            const val Android = "android"
            const val AndroidExtensions = "kotlin-android-extensions"
            const val Kapt = "kotlin-kapt"
            const val Kotlin = "kotlin"
        }

        object Android {
            const val Application = "com.android.application"
            const val Library = "com.android.library"
        }

        const val Detekt = "io.gitlab.arturbosch.detekt"
        const val Ktlint = "org.jlleitschuh.gradle.ktlint"
        const val Version = "com.github.ben-manes.versions"
    }
}

object Versions {
    object Plugin {
        const val Detekt = "1.8.0"
        const val Gradle = "3.6.3"
        const val Kotlin = "1.3.72"
        const val Ktlint = "9.2.1"
        const val Version = "0.28.0"
    }

    object AndroidX {
        const val AppCompat = "1.1.0"
        const val ConstraintLayout = "1.1.3"
        const val CoreKt = "1.2.0"
        const val Lifecycle = "2.2.0"
        const val RecyclerView = "1.1.0"
        const val SwipeRefreshLayout = "1.0.0"
    }

    object OkHttp {
        const val OkHttp = "4.5.0"
        const val AndroidSupport = "3.13.1"
    }

    object Picasso {
        const val Picasso = "2.71828"
        const val OkHttpDownloader = "1.1.0"
    }

    object Kotlin {
        const val Kotlin = Plugin.Kotlin
        const val Coroutines = "1.3.5"
    }

    const val Koin = "2.1.5"
    const val Ktlint = "0.36.0"
    const val Material = "1.1.0"
    const val Retrofit = "2.8.1"
    const val Timber = "4.7.1"

    object Testing {
        object AndroidX {
            const val Test = "1.2.0"
            const val TestExt = "1.1.1"
        }

        const val EspressoCore = "3.2.0"
        const val JUnit = "4.13"
    }
}

object Libraries {
    object AndroidX {
        object Lifecycle {
            const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.Lifecycle}"
            const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.Lifecycle}"
        }

        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.AppCompat}"
        const val ConstraintLayout = "com.android.support.constraint:constraint-layout:${Versions.AndroidX.ConstraintLayout}"
        const val CoreKtx = "androidx.core:core-ktx:${Versions.AndroidX.CoreKt}"
        const val RecyclerView = "androidx.recyclerview:recyclerview:${Versions.AndroidX.RecyclerView}"
        const val SwipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.SwipeRefreshLayout}"
    }

    object Google {
        const val Material = "com.google.android.material:material:${Versions.Material}"
    }

    object Koin {
        const val Core = "org.koin:koin-core:${Versions.Koin}"
        const val Android = "org.koin:koin-android:${Versions.Koin}"
        const val Scope = "org.koin:koin-androidx-scope:${Versions.Koin}"
        const val ViewModel = "org.koin:koin-androidx-viewmodel:${Versions.Koin}"
        const val Extension = "org.koin:koin-androidx-ext:${Versions.Koin}"
        const val Test = "org.koin:koin-test:${Versions.Koin}"
    }

    object Kotlin {
        object Coroutines {
            const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.Coroutines}"
            const val Android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.Coroutines}"
        }

        const val StdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.Kotlin}"
    }

    object OkHttp {
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.OkHttp.OkHttp}"
        const val AndroidSupport = "com.squareup.okhttp3:okhttp-android-support:${Versions.OkHttp.AndroidSupport}"
        const val LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp.OkHttp}"
    }

    object Retrofit {
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
        const val MoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.Retrofit}"
    }

    object Picasso {
        const val Picasso = "com.squareup.picasso:picasso:${Versions.Picasso.Picasso}"
        const val OkHttpDownloader = "com.jakewharton.picasso:picasso2-okhttp3-downloader:${Versions.Picasso.OkHttpDownloader}"
    }

    const val Timber = "com.jakewharton.timber:timber:${Versions.Timber}"
}

object TestingLibraries {
    const val JUnit = "junit:junit:${Versions.Testing.JUnit}"
}

object AndroidTestingLibraries {
    object AndroidX {
        const val TestRules = "androidx.test:rules:${Versions.Testing.AndroidX.Test}"
        const val TestRunner = "androidx.test:runner:${Versions.Testing.AndroidX.Test}"
        const val TestExtJUnit = "androidx.test.ext:junit:${Versions.Testing.AndroidX.TestExt}"
    }

    const val EspressoCore = "androidx.test.espresso:espresso-core:${Versions.Testing.EspressoCore}"
}
