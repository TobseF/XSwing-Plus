import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  // Apply the application plugin to add support for building an application in Java.
  application

  id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
  flatDir {
    dirs("lib")
  }
}

dependencies {
  implementation(":gson:2.2.4")
  implementation(":ixbm:")
  implementation(":jinput:")
  implementation(":jnlp:")
  implementation(":jogg:0.0.7")
  implementation(":jorbis:0.0.15")
  implementation(":lwjgl:")
  implementation(":lwjgl:debug")
  implementation(":lwjgl:util")
  implementation(":nifty:1.1")
  implementation(":nifty-default-controls:1.1")
  implementation(":slick:")
  implementation(":tinylinepp:")
  implementation(":xpp3:1.1.4c:")

  testImplementation(":junit:4.13.1")
  testImplementation(":junit-benchmarks:0.7.0")
  testImplementation(":hamcrest-core:1.3")
}

application {
  // The main class for the application.
  mainClass.set("xswing.start.XSwingWithOptions")
}

// Output to build/libs/XSwingPlus.jar
tasks.withType<ShadowJar> {
  archiveClassifier.set("")

  copy {
    into("$buildDir/libs/")
    with(copySpec {
      from(".") {
        include("res/**")
      }
    })
  }
  copy {
    into("$buildDir/libs/")
    with(copySpec {
      from(".") {
        include("*.dll")
        include("*.so")
        include("*.jnilib")
        include("README.md")
        include("config.json")
      }
    })
  }
}

