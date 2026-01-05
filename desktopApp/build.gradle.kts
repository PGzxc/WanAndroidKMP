import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
                implementation("net.harawata:appdirs:1.2.1")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WanAndroidKMP"
            packageVersion = "1.0.0" //后面添加
            vendor = "@zxc"
            description = "WanAndroid"
            copyright = "© 2023  . All rights reserved."
//            macOS{
//                dockName = "WanAndroid"
//                iconFile.set(project.file("icons/android_1.png"))
//            }
//            linux{
//                iconFile.set(project.file(("icons/android_2.png")))
//            }
//            windows{
//                shortcut = true
//                dirChooser = true
//                upgradeUuid = "1"
//                iconFile.set(project.file("icons/android_3.png"))
//            }
        }
    }
}
