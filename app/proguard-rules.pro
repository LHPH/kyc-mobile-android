# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-------------------------------------------------------------------------
# Core Android & Kotlin 2.2 Rules
#-------------------------------------------------------------------------
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,SourceFile,LineNumberTable

# Maintain Kotlin metadata and reflection capabilities
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.reflect.**

#-------------------------------------------------------------------------
# Jetpack Compose Rules
#-------------------------------------------------------------------------
# Prevent Compose runtime from stripping internal structural requirements
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
    @androidx.compose.runtime.ReadOnlyComposable *;
}

#-------------------------------------------------------------------------
# Retrofit & OkHttp Rules
#-------------------------------------------------------------------------
# Retain Retrofit interfaces and their method parameters
-keepattributes RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,RuntimeVisibleParameterAnnotations
-dontwarn retrofit2.**

-dontwarn okhttp3.**

# Prevent obfuscation of your API interface classes
-keep interface com.kyc.mobile.data.remote.api.** { *; }

#-------------------------------------------------------------------------
# Custom Annotations & Interceptors (com.kyc.mobile)
#-------------------------------------------------------------------------
# Keep your custom annotation classes intact
-keep @interface com.kyc.mobile.data.annotation.**

# Keep methods and classes annotated with your custom annotations
-keep @com.kyc.mobile.data.remote.interceptors.** class * { *; }
-keepclassmembers class * {
    @com.kyc.mobile.data.remote.interceptors.** *;
}

# Keep OkHttp Interceptor implementations
-keep class * implements okhttp3.Interceptor { *; }

#-------------------------------------------------------------------------
# Room 3 Rules
#-------------------------------------------------------------------------
-keep class * extends androidx.room3.RoomDatabase
-dontwarn androidx.room3.**

# Keep your Entity classes (data models) from being obfuscated or stripped
# Change the subpackage path below if your models live elsewhere
-keep interface com.kyc.mobile.data.local.dao.** { *; }
-keep class com.kyc.mobile.data.local.dao.** { *; }
-keep class com.kyc.mobile.data.local.entity.** { *; }

#-------------------------------------------------------------------------
# Room 3 Custom Type Converters
#-------------------------------------------------------------------------
# 1. Keep the @TypeConverters annotation structural metadata
-keepattributes *Annotation*

# 2. Prevent obfuscating classes and methods annotated with Room's converter annotations
-keepclassmembers class * {
    @androidx.room3.TypeConverter <methods>;
}

# 3. Explicitly preserve converter classes in your local data hierarchy
# (Change the .converter package path if your converters live in a different subpackage)
-keep class com.kyc.mobile.data.local.rooom.converter.** { *; }
-keep class * {
    @androidx.room3.TypeConverters <fields>;
}

# Kotlinx Serialization Rules
#-------------------------------------------------------------------------
# Keep the serializable classes and their companion objects
-keep @kotlinx.serialization.Serializable class * { *; }

# Keep the generated $serializer classes required for decoding/encoding
-keep class *$$serializer { *; }
-keepclassmembers class * {
    *** Companion;
    *** serializer(...);
}

#-------------------------------------------------------------------------
# Android Lifecycle & ViewModel Rules (For Manual DI)
#-------------------------------------------------------------------------
# Since you instantiate ViewModels manually (e.g., via ViewModelProvider.Factory),
# prevent R8 from stripping constructors that the system needs to instantiate them.
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

#-------------------------------------------------------------------------
#  Coroutines & Flow Rules (Kotlin 2.2 Core)
#-------------------------------------------------------------------------
# Room 3 and Retrofit both rely heavily on Kotlin Coroutines and Flows.
# These rules prevent R8 from over-optimizing the internal state machines of Coroutines.
-dontwarn kotlinx.coroutines.**
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep ServiceLoader configuration files for Coroutines to load the Main dispatcher
-keepattributes ServiceLoader

#-------------------------------------------------------------------------
# 9. Jetpack Navigation Compose Rules
#-------------------------------------------------------------------------
# Prevent R8 from removing navigation runtime internal structures
-keep class androidx.navigation.compose.** { *; }
-dontwarn androidx.navigation.compose.**

# If you use Type-Safe Navigation (passing @Serializable classes/objects as routes):
# This keeps the class names and structures intact so the Navigation Compose
# compiler plugin can correctly generate route paths from your types.
# Prevent R8 from removing the static INSTANCE field on your Route objects
-keepclassmembers @kotlinx.serialization.Serializable class com.kyc.mobile.ui.navigation.** {
    public static ** INSTANCE;
}

