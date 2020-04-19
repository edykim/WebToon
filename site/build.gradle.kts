plugins {
    androidLibrary()
    kotlinAndroid()
}

android {
    setDefaultConfig()
    setLibraryProguard(project)
}

dependencies {
    implementation(Dep.Kotlin.stdlibJvm)

    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(Dep.Kotlin.coroutinesCore)
    // Jsoup
    implementation(Dep.jsoup)

    testImplementation(Dep.Test.junit)
}

apply(from = "../publish_local.gradle")