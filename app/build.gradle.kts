import org.gradle.external.javadoc.StandardJavadocDocletOptions
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.dsl.ApplicationExtension

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.listycity"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.listycity"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.3")

    // FIX: Add this line to allow Gradle to launch the JUnit Platform
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

// Fixed Javadoc Task for modern Gradle/AGP
val androidComponents = extensions.getByType<ApplicationAndroidComponentsExtension>()

androidComponents.onVariants { variant ->
    val taskName = "generate${variant.name.replaceFirstChar { it.uppercase() }}Javadoc"

    tasks.register<Javadoc>(taskName) {
        description = "Generates Javadoc for ${variant.name}."
        group = "documentation"

        // Source: Points to your Java files
        val mainSource = File(projectDir, "src/main/java")
        source = fileTree(mainSource).matching { include("**/*.java") }

        // Destination: Exactly as required by Step 2
        destinationDir = file("$projectDir/javadocs")

        isFailOnError = false

        doFirst {
            val javaCompileTask = tasks.named<JavaCompile>("compile${variant.name.replaceFirstChar { it.uppercase() }}JavaWithJavac").get()

            // Using sdkComponents to find the bootClasspath (which includes android.jar)
            classpath = javaCompileTask.classpath + files(androidComponents.sdkComponents.bootClasspath.get())

            (options as StandardJavadocDocletOptions).apply {
                addStringOption("Xdoclint:none", "-quiet")
                addStringOption("encoding", "UTF-8")
                addStringOption("charSet", "UTF-8")
            }
        }
    }
}

// Required to run JUnit 5 tests
tasks.withType<Test> {
    useJUnitPlatform()
}