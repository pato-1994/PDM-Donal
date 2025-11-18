
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "edu.upiita.ipn.pdm.orm_room"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.upiita.ipn.pdm.orm_room"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// Archivo: app/build.gradle.kts

// ... (sección de plugins y android)

dependencies {
    // Dependencias de AndroidX y Compose (estas ya están bien en tu proyecto)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- SECCIÓN DE FIREBASE CORREGIDA Y LIMPIA ---

    // 1. Importa el catálogo oficial de Firebase (BOM).
    //    Esto le dice a Gradle: "Usa este catálogo para decidir las versiones de Firebase".
    //    Estoy usando una versión reciente y estable del BOM.
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    // 2. Añade la librería de Firestore SIN especificar la versión.
    //    El BOM se encargará de seleccionar la versión correcta y compatible automáticamente.
    implementation("com.google.firebase:firebase-firestore-ktx")

    // --- NUEVAS DEPENDENCIAS PARA CONEXIÓN A API REST ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // ----------------------------------------------------

    // Dependencias de Compose y Navegación
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.navigation.compose)

    // Dependencias de Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
