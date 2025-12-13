dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Reference the TOML file in the root project's gradle/ directory
            from(files("../gradle/libs.versions.toml"))
        }
    }
}