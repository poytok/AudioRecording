plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  id("kotlin-kapt")
  alias(libs.plugins.googleDaggerHilt)
  alias(libs.plugins.googleServices)

}

android {
  namespace = "jjh.preinterview"
  compileSdk = 34

  defaultConfig {
    applicationId = "jjh.preinterview"
    minSdk = 29
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}
subprojects {
  afterEvaluate {
    project.apply { "$rootDir/common.gradle.kts" }
  }
}

dependencies {
  implementation(project(":data"))
  implementation(project(":domain"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)

  // hilt
  implementation(libs.hilt.android)
  implementation(libs.androidx.hilt.navigation.compose)
  kapt(libs.hilt.android.compiler)

  // navigation
  implementation(libs.androidx.navigation.compose)

  // ONNX
  implementation(libs.onnxruntime.android)
//  implementation ("com.microsoft.onnxruntime:onnxruntime-extensions-android:latest.release")


  // Retrofit
  implementation(libs.retrofit)

  // Gson
  implementation(libs.gson)
  implementation(libs.converter.gson)

  // OkHttp
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)

  // Logger
  implementation(libs.logger)

  // SSE
  implementation(libs.okhttp.eventsource)

  // google login
  implementation(platform(libs.firebase.bom))
  //noinspection UseTomlInstead
  implementation("com.google.firebase:firebase-auth-ktx")
  implementation(libs.play.services.auth)

  // Datastore
  implementation(libs.androidx.datastore.preferences)


  debugImplementation("androidx.customview:customview:1.2.0-alpha02")
  debugImplementation("androidx.customview:customview-poolingcontainer:1.0.0")

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}