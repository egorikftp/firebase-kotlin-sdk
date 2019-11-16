import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.3.50" apply false
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.2")
    }
}

val targetSdkVersion by extra(28)
val minSdkVersion by extra(14)

subprojects {

    group = "dev.teamhub.firebase"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        jcenter()
    }

    tasks.withType<KotlinCompile<*>> {
        kotlinOptions.freeCompilerArgs += listOf(
            "-Xuse-experimental=kotlin.Experimental",
            "-Xuse-experimental=kotlinx.serialization.ImplicitReflectionSerializer"
        )
    }

    afterEvaluate  {
        dependencies {
            "commonMainImplementation"(kotlin("stdlib-common"))
            "commonMainImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.2")
            "jsMainImplementation"(kotlin("stdlib-js"))
            "jsMainImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.2")
            "androidMainImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
            "androidMainImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.2")
            "jvmMainImplementation"(kotlin("stdlib-jdk8"))
            "jvmMainImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
            "jvmMainApi"("app.teamhub:firebase-java:0.2.0")
            "jvmMainApi"("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.2")
        }
    }
}

