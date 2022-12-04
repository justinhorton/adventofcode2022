plugins {
    kotlin("multiplatform") version "1.7.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val hostArch = System.getProperty("os.arch")

    logger.lifecycle("OS: $hostOs, Arch: $hostArch")

    val nativeTarget = when {
        hostOs == "Mac OS X" -> if (hostArch == "aarch64") macosArm64("native") else macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> throw GradleException("Host OS/Arch combo not supported")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "xyz.justinhorton.aoc2022.main"
                baseName = "aocrunner"
            }
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
                implementation("com.squareup.okio:okio:3.2.0")
                implementation(kotlin("reflect"))
            }
        }
        val nativeTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.register("runAll") {
    doLast {
        exec {
            commandLine("sh", "./run.sh")
        }
    }
    
    dependsOn.add(tasks.getByName("assemble"))
}

tasks.getByName("check") {
    dependsOn.add(tasks.getByName("runAll"))
}
