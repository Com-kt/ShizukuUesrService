import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "app.compile"
    compileSdk = 37
    buildToolsVersion = "36.1.0"
    
    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    defaultConfig {
        applicationId = "com.kitty.shizuku"
        minSdk = 24
        targetSdk = 37
        versionCode = 33
        versionName = "0.7.0-20260515-xiaomi-version"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    signingConfigs {
        create("url") {
        // keystore file，.bks & .jks
            storeFile = file("keystore/xiao.keystore")
            storePassword = findProperty("XIAO_KEY_PASSWORD") as String
            keyAlias = findProperty("XIAO_KEY_ALIAS") as String
            keyPassword = findProperty("XIAO_KEY_KEYPASSWORD") as String
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("url")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("url")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        aidl = true
        buildConfig = true
    }
    
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    
    lint {
        htmlReport = true
        xmlReport = true
        textReport = true
        sarifReport = true
        // disable("ProtectedPermissions")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    
}

tasks.withType<KotlinJvmCompile>()
    .configureEach {
        compilerOptions
            .jvmTarget
            .set(
                JvmTarget.JVM_21
            )
    }

dependencies {
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.shizuku)
    runtimeOnly(libs.bundles.coroutines.runtime)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.google.gson)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.annotation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    implementation(libs.mt.dataFilesProvider)
}