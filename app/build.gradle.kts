plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	id("kotlin-kapt")
	id("com.google.dagger.hilt.android")
	kotlin("plugin.serialization") version "2.0.21"
	alias(libs.plugins.compose.compiler)
	id("kotlin-parcelize")
	id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
	namespace = "com.example.worlddata"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.worlddata"
		minSdk = 24
		targetSdk = 34
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}

	buildFeatures {
		compose = true

		composeOptions {
			kotlinCompilerExtensionVersion = libs.versions.kotlin.toString()
		}
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.fragment.ktx)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	kapt(libs.androidx.room.compiler)
	kapt(libs.hilt.android.compiler)
	implementation(libs.hilt.android)
	implementation(libs.androidx.room.runtime)
	implementation (libs.androidx.room.ktx)
	implementation(libs.kotlinx.coroutines.core)
	implementation(libs.kotlinx.coroutines.android)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.glide)
	annotationProcessor(libs.compiler)

	val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
	implementation(composeBom)
	androidTestImplementation(composeBom)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.ui.tooling.preview)
	debugImplementation(libs.androidx.ui.tooling)
	implementation(libs.androidx.activity.compose)
	implementation(libs.coil)
	implementation(libs.coil.svg)
	implementation (libs.lottie)
	implementation (libs.play.services.maps)
}

kapt {
	correctErrorTypes = true
}

secrets {
	propertiesFileName = "secrets.properties"
	defaultPropertiesFileName = "local.defaults.properties"
	ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
	ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}