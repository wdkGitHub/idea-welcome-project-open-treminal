plugins {
    id("java")
//    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    id("org.jetbrains.intellij") version "1.16.1"
}

group = "dekun.wang"
//version = "1.0.1-SNAPSHOT"
version = "1.0.6"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.2")
    type.set("IC") // Target IDE Platform
//    plugins.set(listOf(/* Plugin Dependencies */))
    updateSinceUntilBuild.set(false)//多版本兼容，不然根据构建版本设置最大兼容版本
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
    }
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "11"
//    }

    patchPluginXml {
        sinceBuild.set("222")
//        untilBuild.set("*.*")
    }

//    signPlugin {
//        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
//        privateKey.set(System.getenv("PRIVATE_KEY"))
//        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
//    }
//
//    publishPlugin {
//        token.set(System.getenv("PUBLISH_TOKEN"))
//    }
}

