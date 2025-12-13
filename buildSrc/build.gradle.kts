plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
}
dependencies {
    implementation(libs.jackson.dataformat.yml)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.module.kotlin)
}