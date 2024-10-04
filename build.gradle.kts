// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.7.22")
    repositories {
        google()
        mavenCentral()
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://jcenter.bintray.com")
        }

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

val versionMajor = 0
val versionMinor = 17
val versionPatch = 0
val versionBuild = 0 // bump for dogfood builds, public betas, etc.
val versionCode = versionMajor * 100000 + versionMinor * 1000 + versionPatch * 100 + versionBuild

extra.apply {
    set("versionCode", versionCode)
    set("versionName", "$versionMajor.$versionMinor.$versionPatch")
    set("appCompatVer", "1.4.2")
    set("junitVer", "4.13.2")
    set("androidJunitVer", "1.1.3")
    set("espressoVer", "3.4.0")
    set("jacksonVer", "2.11.1")
    set("commonsIoVer", "2.5") // supports java 1.6
    set("commonsCompressVer", "1.12") // supports java 1.6
    set("coreKtxVer", "1.8.0")
}

allprojects {
    group = "com.github.yausername"
    version = versionCode
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

tasks.register("packagePublishedArtifacts") {
    doFirst("publishToMavenLocal") {
        exec {
            workingDir = File(System.getProperty("user.home"), ".m2/repository")
            standardOutput = System.out
            errorOutput = System.err

            commandLine("pwd")
            commandLine(
                "zip",
                "-r",
                "$projectDir/archive-$versionCode.zip",
                "io/github/junkfood02/youtubedl-android"
            )
        }
    }
}